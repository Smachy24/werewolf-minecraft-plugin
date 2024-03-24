package werewolf.plugin.minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import werewolf.plugin.minecraft.Game;
import werewolf.plugin.minecraft.GameConfiguration;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.phases.RoundPhase;
import werewolf.plugin.minecraft.scoreboards.CompositionObjective;

public class StartCommand implements CommandExecutor {
    public static Game game;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            if(s.equalsIgnoreCase("start")) {
                this.removeCompositionScoreboard();
                Bukkit.getServer().broadcastMessage("La partie se lance");
                loadCompositionScoreboard();
                game = new Game(GameConfiguration.getInstance().getGameRoles());
                game.startGame();
                RoundPhase firstRound = new RoundPhase(1);
                return true;
            }
        }
        return false;
    }

    private void loadCompositionScoreboard(){
        CompositionObjective compositionObjective = new CompositionObjective();
        for(Player player: Bukkit.getOnlinePlayers()){
            compositionObjective.setScoreBoard(player);
        }
    }

    private void removeCompositionScoreboard() {
        Scoreboard mainScoreboard = Main.getInstance().getMainScoreboard();
        Objective compositionObjective = mainScoreboard.getObjective("Composition");
        if(compositionObjective != null) {
            compositionObjective.unregister();
        }
    }

    public static Game getCurrentGame() {
        return StartCommand.game;
    }
}
