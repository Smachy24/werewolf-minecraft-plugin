package werewolf.plugin.minecraft.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ConfigGui implements Listener {

    private String configItemName = ChatColor.YELLOW + "Config";
    private Material configItem = Material.COMPASS;
    @EventHandler
    public void interact(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        Action action = event.getAction();

        if(action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
            if(itemStack.getType() == configItem) {
                if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(configItemName)) {

                    Inventory inventory = Bukkit.createInventory(null, 54, configItemName);

                    for(Player players: Bukkit.getOnlinePlayers()) {

                        ItemStack tete = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta meta = (SkullMeta) tete.getItemMeta();
                        meta.setOwner(players.getName());
                        meta.setDisplayName(ChatColor.RED + players.getName());
                        tete.setItemMeta(meta);

                        inventory.addItem(tete);
                    }

                    player.openInventory(inventory);
                }
            }
        }
    }
}
