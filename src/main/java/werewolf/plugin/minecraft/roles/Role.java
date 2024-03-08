package werewolf.plugin.minecraft.roles;

import org.bukkit.ChatColor;
import werewolf.plugin.minecraft.utils.ConfigItem;

public class Role {
    private String name;
    private String team;
    private String frenchName;

    private String description;
    private ConfigItem configItem;
    private ChatColor color;

    public Role() {
    }

    public Role(String name, String team, String frenchName, String description, ConfigItem configItem) {
        this.name = name;
        this.team = team;
        this.frenchName = frenchName;
        this.description = description;
        this.configItem = configItem;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getFrenchName() {
        return frenchName;
    }

    public void setFrenchName(String frenchName) {
        this.frenchName = frenchName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ConfigItem getConfigItem() {
        return configItem;
    }

    public void setConfigItem(ConfigItem configItem) {
        this.configItem = configItem;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

}
