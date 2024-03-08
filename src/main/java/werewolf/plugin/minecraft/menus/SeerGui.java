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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.roles.Role;

import java.util.List;


public class SeerGui implements Listener {

    public static Inventory createInventoryGamePlayersHead(List<GamePlayer> gamePlayerList){
        Inventory inventory = Bukkit.createInventory(null, 18, "Voir le rôle de");

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
                    player.closeInventory();
                    event.setCancelled(true);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }



}
