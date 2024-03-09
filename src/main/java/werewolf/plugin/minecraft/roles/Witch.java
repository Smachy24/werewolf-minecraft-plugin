package werewolf.plugin.minecraft.roles;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import werewolf.plugin.minecraft.utils.ConfigItem;

public class Witch extends Role{


    public Witch() {
    }
    public Witch(String name, String team, String frenchName, String description, ConfigItem configItem) {
        super(name, team, frenchName, description, configItem);
        super.setColor(ChatColor.GREEN);
    }


}
