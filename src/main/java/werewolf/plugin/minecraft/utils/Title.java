package werewolf.plugin.minecraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.commands.StartCommand;

public class Title {

    public static void sendTitleToEveryone(String title, String subtitle){
        for(GamePlayer gamePlayer: StartCommand.getCurrentGame().getAliveGamePlayer()) {
            gamePlayer.getPlayer().sendTitle(title, subtitle, 10, 70, 10);
        }
    }

    public static void sendTitleToPlayer(Player player, String title, String subtitle){
            player.sendTitle(title, subtitle, 10, 70, 10);

    }
}
