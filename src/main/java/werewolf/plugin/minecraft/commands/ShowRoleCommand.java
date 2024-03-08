package werewolf.plugin.minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import werewolf.plugin.minecraft.Game;
import werewolf.plugin.minecraft.GamePlayer;

public class ShowRoleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(s.equalsIgnoreCase("role")) {
                Game game = StartCommand.getCurrentGame();
                if(game != null) {
                    GamePlayer gamePlayer = game.getGamePlayerByPlayer(player);
                    if(gamePlayer != null) {
                        gamePlayer.showRole();
                        return true;
                    }
                }
                player.sendMessage(ChatColor.RED + "La partie n'a pas encore commencé !");
                return true;
            }
        }
        return false;
    }


}
