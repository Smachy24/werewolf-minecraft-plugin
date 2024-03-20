package werewolf.plugin.minecraft.roles;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.utils.ConfigItem;

public class Seer extends Role{

    public Seer() {
    }
    public Seer(String name, String team, String frenchName, String description, ConfigItem configItem) {
        super(name, team, frenchName, description, configItem);
        super.setColor(ChatColor.GREEN);
    }
}
