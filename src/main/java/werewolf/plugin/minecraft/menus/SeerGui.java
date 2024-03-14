package werewolf.plugin.minecraft.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.phases.roles.SeerPhase;
import werewolf.plugin.minecraft.roles.Role;

import java.util.*;


public class SeerGui implements Listener {


    private SeerPhase seerPhase;
    private GamePlayer gamePlayer;
    private boolean isChoiceValidated;
    private final UUID inventoryId;
    private Inventory inventory;

    public SeerGui(SeerPhase seerPhase, GamePlayer gamePlayer) {
        this.seerPhase = seerPhase;
        this.gamePlayer = gamePlayer;
        this.isChoiceValidated = false;
        this.inventoryId = UUID.randomUUID();
    }

    public boolean isChoiceValidated() {
        return isChoiceValidated;
    }

    public void setChoiceValidated(boolean choiceValidated) {
        isChoiceValidated = choiceValidated;
    }


    public Inventory createInventorySeer(List<GamePlayer> gamePlayerList){
        /*
        * Create inventory
        */
        Inventory inventory = Bukkit.createInventory(null, 18, this.inventoryId.toString());
        for(GamePlayer gamePlayer: gamePlayerList){
            if(gamePlayer.getPlayer().getGameMode()== GameMode.SURVIVAL){
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                meta.setOwner(gamePlayer.getPlayer().getName());
                meta.setDisplayName(gamePlayer.getPlayer().getName());
                head.setItemMeta(meta);
                inventory.addItem(head);
            }
        }
        this.inventory = inventory;
        return this.inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (gamePlayer.getPlayer().equals((Player) event.getWhoClicked())) {
            if(event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getTopInventory()) && event.getView().getTitle().equalsIgnoreCase(this.inventoryId.toString())) {
                InventoryAction action = event.getAction();
                ItemStack clickedItem = event.getCurrentItem();
                if(clickedItem!= null) {
                    if(action == InventoryAction.PICKUP_ALL) {
                        Bukkit.broadcastMessage(ChatColor.GOLD + gamePlayer.getPlayer().getName() + " : " + this.isChoiceValidated + " / " + this.inventoryId);
                        String clickedPlayerName = ChatColor.stripColor(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName());
                        GamePlayer clickedGamePlayer = StartCommand.getCurrentGame().getGamePlayerByPlayerName(clickedPlayerName);
                        Role clickedGamePlayerRole = clickedGamePlayer.getRole();
                        this.isChoiceValidated = true;
                        this.gamePlayer.getPlayer().sendMessage(ChatColor.BLUE + "Le rôle de " + clickedPlayerName + " est : " + clickedGamePlayerRole.getColor() + clickedGamePlayerRole.getFrenchName());
                        this.gamePlayer.getPlayer().closeInventory();
                        this.seerPhase.checkIfPhaseTerminated();
                        this.seerPhase.removeInventory(this);
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClosed(InventoryCloseEvent e) {
        if (gamePlayer.getPlayer().equals(e.getPlayer())) {
            if (this.inventoryId != null) {
                if (e.getView().getTitle().equalsIgnoreCase(this.inventoryId.toString())) {
                    if (!this.isChoiceValidated) {
                        Inventory closedInventory = e.getInventory();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                gamePlayer.getPlayer().openInventory(closedInventory);
                            }
                        }.runTaskLater(Main.getInstance(), 1L);
                    }
                }
            }
        }
    }

}
