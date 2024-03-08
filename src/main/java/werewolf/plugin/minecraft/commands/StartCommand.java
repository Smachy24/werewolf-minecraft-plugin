package werewolf.plugin.minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import werewolf.plugin.minecraft.Game;
import werewolf.plugin.minecraft.GameConfiguration;
import werewolf.plugin.minecraft.scoreboards.CompositionScoreboard;

public class StartCommand implements CommandExecutor {
    public static Game game;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            if(s.equalsIgnoreCase("start")) {
                Bukkit.getServer().broadcastMessage("La partie se lance");
                loadCompositionScoreboard();
                game = new Game(GameConfiguration.getInstance().getGameRoles());
                game.playGame();
                return true;
            }
        }
        return false;
    }

    private void loadCompositionScoreboard(){
        for(Player player: Bukkit.getOnlinePlayers()){
            CompositionScoreboard.setScoreBoard(player);
        }
    }

    public static Game getCurrentGame() {
        return StartCommand.game;
    }
}
