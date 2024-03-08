package werewolf.plugin.minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import werewolf.plugin.minecraft.scoreboards.CompositionScoreboard;

public class TestRemoveRole implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            if(s.equalsIgnoreCase("test-remove-role")){
                updateCompositionScoreboard();
                return true;
            }
        }
        return false;
    }

    private void updateCompositionScoreboard(){
        for(Player player: Bukkit.getOnlinePlayers()){
            CompositionScoreboard.updateScoreBoard(player);
        }
    }
}
