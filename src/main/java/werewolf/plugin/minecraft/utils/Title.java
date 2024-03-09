package werewolf.plugin.minecraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
public class Title {

    public static void sendTitleToEveryone(String title, String subtitle){
        for(Player player: Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subtitle, 10, 70, 10);
        }
    }

    public static void sendTitleToPlayer(Player player, String title, String subtitle){
            player.sendTitle(title, subtitle, 10, 70, 10);

    }
}
