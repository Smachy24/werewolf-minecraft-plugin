package werewolf.plugin.minecraft.roles;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.utils.ConfigItem;

public class Werewolf extends Role{

    private Player nightPlayerVoted = null;

    public Werewolf(String name, String team, String frenchName, String description, ConfigItem configItem) {
        super(name, team, frenchName, description, configItem);
        super.setColor(ChatColor.RED);
    }

    public Player getNightPlayerVoted() {
        return nightPlayerVoted;
    }

    public void setNightPlayerVoted(Player nightPlayerVoted) {
        this.nightPlayerVoted = nightPlayerVoted;
    }
}
