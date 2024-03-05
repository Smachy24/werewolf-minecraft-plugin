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

        addRoleSection(inventory, RolesConfiguration.getVillagerRoles(), ChatColor.GREEN ,9);

        // Add a line of barrier blocks
        addBarrierLine(inventory, 27);

        // Add roles for Werewolves
        addRoleSection(inventory, RolesConfiguration.getWerewolvesRoles(), ChatColor.RED, 36);

        addRoleSection(inventory, RolesConfiguration.getNeutralRoles(), ChatColor.GOLD, 36);

        return inventory;
    }

    private static void addBarrierLine(Inventory inventory, int startIndex) {
        for (int i = 0; i < 9; i++) {
            ItemStack barrier = new ItemStack(Material.BARRIER);
            ItemMeta meta = barrier.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD + "Barrier");
            barrier.setItemMeta(meta);
            inventory.setItem(startIndex + i, barrier);
        }
    }

    private static void addRoleSection(Inventory inventory, List<Role> roles, ChatColor color, int startIndex) {
        for (int i = 0; i < Math.min(roles.size(), 18); i++) {
            Role role = roles.get(i);
            ItemStack item = new ItemStack(role.getConfigItem().getMaterial());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(color + role.getFrenchName());
            item.setItemMeta(meta);
            inventory.setItem(startIndex + i, item);
        }
    }

}
