package werewolf.plugin.minecraft.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.roles.Role;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RolesConfiguration implements Listener {
    private static final String ROLES_FILE = "/roles-list.yml";
    private static List<Role> configRoles = new ArrayList<>();

    private static String configItemName = ChatColor.YELLOW + "Config";
    private static Material configItem = Material.COMPASS;

    public static List<Role> getConfigRoles() {
        return configRoles;
    }
    public static List<Role> getVillagerRoles() {
        return filterRolesByTeam("Villagers");
    }

    public static List<Role> getWerewolvesRoles() {
        return filterRolesByTeam("Werewolves");
    }

    public static List<Role> getNeutralRoles() {
        return filterRolesByTeam("Neutral");
    }

    private static List<Role> filterRolesByTeam(String team) {
        return configRoles.stream()
                .filter(role -> role.getTeam().equalsIgnoreCase(team))
                .collect(Collectors.toList());
    }

    public static void setConfigRoles(List<Role> configRoles) {
        RolesConfiguration.configRoles = configRoles;
    }

    static {
        getFileRoles();
    }

    private static void getFileRoles() {
        try (InputStream inputStream = Main.class.getResourceAsStream(ROLES_FILE)) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            List<Role> roles = mapper.readValue(inputStream, mapper.getTypeFactory().constructCollectionType(List.class, Role.class));
            for (Role role : roles) {
                createEachRole(role);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier de rôles", e);
        }
    }

    private static void createEachRole(Role role) {
        try {
            String className = "werewolf.plugin.minecraft.roles." + role.getName();
            Class<?> roleClass = Class.forName(className);
            Role instantiatedRole = (Role) roleClass.getDeclaredConstructor(String.class, String.class, String.class, String.class, ConfigItem.class)
                    .newInstance(role.getName(), role.getTeam(), role.getFrenchName(), role.getDescription(), role.getConfigItem());

            configRoles.add(instantiatedRole);
        } catch (Exception e) {
            throw new RuntimeException("Error creating role: " + role.getName(), e);
        }
    }

    public static void getItemConfig(Player player) {
        ItemStack item = new ItemStack(configItem);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(configItemName);
        item.setItemMeta(itemMeta);
        player.getInventory().setItem(4, item);
    }
}
