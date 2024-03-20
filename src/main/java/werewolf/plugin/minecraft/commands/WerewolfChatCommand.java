package werewolf.plugin.minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import werewolf.plugin.minecraft.GamePlayer;

import java.util.List;

public class WerewolfChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(s.equalsIgnoreCase("lg-chat")) {
                if(StartCommand.getCurrentGame()!=null) {
                    GamePlayer currentGamePlayer = StartCommand.getCurrentGame().getGamePlayerByPlayer(player);
                    if(currentGamePlayer.getRole().getTeam().equalsIgnoreCase("Werewolves")) {
                        if(strings.length > 0) {
                            String playerMessage = String.join(" ", strings);
                            List<GamePlayer> gamePlayers = StartCommand.getCurrentGame().getGamePlayersByRoleName("Werewolf");
                            for(GamePlayer gamePlayer: gamePlayers) {
                                gamePlayer.getPlayer().sendMessage(ChatColor.RED + "<"+player.getName()+"> " + ChatColor.WHITE +  playerMessage);
                            }
                            return true;
                        }
                        else {
                            player.sendMessage(ChatColor.GOLD + "Attention le message est vide");
                            return true;
                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Vous n'êtes pas loup-garou, vous ne pouvez pas utilise cette commande");
                        return true;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "La partie n'a pas commencé");
                    return true;
                }
            }
        }
        return false;
    }
}
