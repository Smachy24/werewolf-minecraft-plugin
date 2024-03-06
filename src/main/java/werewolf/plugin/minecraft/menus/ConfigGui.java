package werewolf.plugin.minecraft.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import werewolf.plugin.minecraft.GameConfiguration;
import werewolf.plugin.minecraft.roles.Role;
import werewolf.plugin.minecraft.utils.Gui;
import werewolf.plugin.minecraft.utils.RolesConfiguration;

import java.util.ArrayList;
import java.util.List;

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
                    Inventory inventory = createInventoryConfigRoles(54, configItemName);
                    player.openInventory(inventory);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getTopInventory())) {
            Player player = (Player) event.getWhoClicked();
            InventoryAction action = event.getAction();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem != null) {
                if (clickedItem.getType() == Material.BARRIER) {
                    event.setCancelled(true);
                }

                if (action == InventoryAction.PICKUP_ALL) {
                    addRole(event, player, clickedItem);
                } else if (action == InventoryAction.PICKUP_HALF) {
                    removeRole(event, player, clickedItem);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    private void addRole(InventoryClickEvent event, Player player, ItemStack clickedItem){
        for (Role role : RolesConfiguration.getConfigRoles()) {
            if (clickedItem.getType() == role.getConfigItem().getMaterial()) {

                if(GameConfiguration.getInstance().containsRole(role)){
                    clickedItem.setAmount(clickedItem.getAmount()+1);

                } else {
                    clickedItem.addUnsafeEnchantment(Enchantment.LUCK, 1);
                    ItemMeta meta = clickedItem.getItemMeta();
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    clickedItem.setItemMeta(meta);
                }

                GameConfiguration.getInstance().addRole(role);
                player.sendMessage(ChatColor.GREEN + "Role added: " + role.getFrenchName());
                event.setCancelled(true);
                return;
            }
        }
    }

    private void removeRole(InventoryClickEvent event, Player player, ItemStack clickedItem) {
        List<Role> roleOccurences = countRoleOccurrences(clickedItem);

        if(roleOccurences.size()==0) {
            event.setCancelled(true);
        }
        Role roleToRemove = roleOccurences.get(0);

        if (roleOccurences.size() >= 2) {
            clickedItem.setAmount(clickedItem.getAmount()-1);

        } else {
            clickedItem.removeEnchantments();
        }

        GameConfiguration.getInstance().removeRole(roleToRemove);
        player.sendMessage(ChatColor.RED + "Role removed: " + roleToRemove.getFrenchName());
        event.setCancelled(true);
    }

    private static List<Role> countRoleOccurrences(ItemStack clickedItem) {
        List<Role> roleOccurrences = new ArrayList<>();
        for (Role role : GameConfiguration.getInstance().getGameRoles()) {
            if (clickedItem.getType() == role.getConfigItem().getMaterial() && GameConfiguration.getInstance().containsRole(role)) {
                roleOccurrences.add(role);
            }
        }
        return roleOccurrences;
    }

    private static ItemStack getItemGui(Role role, ChatColor color){
        ItemStack item = new ItemStack(role.getConfigItem().getMaterial());
        List<Role> roleOccurrences = ConfigGui.countRoleOccurrences(item);


        if(roleOccurrences.size()>=1) {
            item.setAmount(roleOccurrences.size());
            item.addUnsafeEnchantment(Enchantment.LUCK, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(color + role.getFrenchName());
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
        else {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(color + role.getFrenchName());
            item.setItemMeta(meta);
        }
        return item;
    }

    public static Inventory createInventoryConfigRoles(int size, String inventoryName){
        Inventory inventory = Bukkit.createInventory(null, size, inventoryName);

        addRoleSection(inventory, RolesConfiguration.getVillagerRoles(), ChatColor.GREEN ,9);
        addBarrierLine(inventory, 27);
        addRoleSection(inventory, RolesConfiguration.getWerewolvesRoles(), ChatColor.RED, 36);
        addRoleSection(inventory, RolesConfiguration.getNeutralRoles(), ChatColor.GOLD, 45);

        return inventory;
    }

    private static void addBarrierLine(Inventory inventory, int startIndex) {
        for (int i = 0; i < 9; i++) {
            ItemStack barrier = new ItemStack(Material.BARRIER);
            ItemMeta meta = barrier.getItemMeta();
            meta.setDisplayName(" ");
            barrier.setItemMeta(meta);
            inventory.setItem(startIndex + i, barrier);
        }
    }

    private static void addRoleSection(Inventory inventory, List<Role> roles, ChatColor color, int startIndex) {
        for (int i = 0; i < Math.min(roles.size(), 18); i++) {
            Role role = roles.get(i);
            inventory.setItem(startIndex + i, ConfigGui.getItemGui(role, color));
        }
    }
}
