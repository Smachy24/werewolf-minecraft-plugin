package werewolf.plugin.minecraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import werewolf.plugin.minecraft.roles.Role;

import java.util.List;

public class Gui {

    public static Inventory createInventoryPlayersHead(int size, String inventoryName){
        Inventory inventory = Bukkit.createInventory(null, size, inventoryName);

        for(Player player: Bukkit.getOnlinePlayers()){
            if(player.getGameMode()== GameMode.SURVIVAL){
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                meta.setOwner(player.getName());
                meta.setDisplayName(ChatColor.RED + player.getName());
                head.setItemMeta(meta);
                inventory.addItem(head);
            }
        }
        return inventory;
    }

    public static Inventory createInventoryConfigRoles(int size, String inventoryName){
        Inventory inventory = Bukkit.createInventory(null, size, inventoryName);

        for(Role role: RolesConfiguration.getConfigRoles()){
            ItemStack item = new ItemStack(role.getConfigItem().getMaterial());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(role.getFrenchName());
            item.setItemMeta(meta);
            inventory.addItem(item);
        }
        return inventory;
    }
}
