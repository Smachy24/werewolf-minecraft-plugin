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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.phases.roles.SeerPhase;
import werewolf.plugin.minecraft.roles.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SeerGui implements Listener {

    private static Map<Inventory, String> inventoryMap = new HashMap<>();

    private static boolean isChoiceValidated = false;

    public static Inventory createInventorySeer(List<GamePlayer> gamePlayerList){
        Inventory inventory = Bukkit.createInventory(null, 18, "Voir le rôle de");
        inventoryMap.put(inventory, "seerInventory");

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
        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getTopInventory())) {
            Player player = (Player) event.getWhoClicked();
            InventoryAction action = event.getAction();
            ItemStack clickedItem = event.getCurrentItem();

            if(clickedItem!= null) {
                if(action == InventoryAction.PICKUP_ALL) {
                    String clickedPlayerName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                    GamePlayer clickedGamePlayer = StartCommand.getCurrentGame().getGamePlayerByPlayerName(clickedPlayerName);
                    Role clickedGamePlayerRole = clickedGamePlayer.getRole();
                    player.sendMessage(ChatColor.BLUE + "Le rôle de " + clickedPlayerName + " est : " + clickedGamePlayerRole.getColor() + clickedGamePlayerRole.getFrenchName());
                    isChoiceValidated = true;
                    player.closeInventory();
                    SeerPhase.stopPhaseEngine();
                    event.setCancelled(true);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClosed(InventoryCloseEvent e) {
        Inventory closedInventory = e.getInventory();

        if (!isChoiceValidated && inventoryMap.containsKey(closedInventory) && inventoryMap.get(closedInventory).equals("seerInventory")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getPlayer().openInventory(closedInventory);
                }
            }.runTaskLater(Main.getInstance(), 1L);
        }
    }
}
