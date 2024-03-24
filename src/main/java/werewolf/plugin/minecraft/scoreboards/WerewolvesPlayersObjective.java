package werewolf.plugin.minecraft.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.phases.roles.WerewolvesPhase;

import java.util.List;

public class WerewolvesPlayersObjective {
    private final Scoreboard mainScoreboard;
    private final WerewolvesPhase werewolvesPhase;
    private final String objectiveSuffix = "_werewolves_votes";

    public WerewolvesPlayersObjective(WerewolvesPhase werewolvesPhase) {
        this.mainScoreboard = Main.getInstance().getMainScoreboard();
        this.werewolvesPhase = werewolvesPhase;
    }

    public void initAllObjectives() {
        this.createAllObjectives();
        for(Player player: Bukkit.getOnlinePlayers()) {
            player.setScoreboard(this.mainScoreboard);
        }
    }

    private void createAllObjectives() {
        for(Player player: Bukkit.getOnlinePlayers()) {
            String objectiveName = player.getName() + objectiveSuffix;
            Objective obj = this.mainScoreboard.registerNewObjective(objectiveName, "dummy", ChatColor.GOLD + "Votes");
            obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }
    }

    public void resetAllObjectives(){
        for(Player player: Bukkit.getOnlinePlayers()) {
            String objectiveName = player.getName() + objectiveSuffix;
            Objective obj = mainScoreboard.getObjective(objectiveName);
            if(obj != null) {
                obj.unregister();
            }
        }
    }

    public void updateScoreboard() {
        // Définir le tableau de bord à l'extérieur de la boucle
        for(Player player: Bukkit.getOnlinePlayers()) {
            String objectiveName = player.getName() + objectiveSuffix;
            Objective obj = mainScoreboard.getObjective(objectiveName);
            if(obj != null) {
                int votesCount = werewolvesPhase.getPhaseVotesCountByPlayer(player);
                Bukkit.broadcastMessage(ChatColor.GREEN +  player.getName() + obj.getScore(player.getName()).getScore());
                obj.getScore(player.getName()).setScore(votesCount);
                Bukkit.broadcastMessage(ChatColor.RED +  player.getName() + obj.getScore(player.getName()).getScore());
            }
        }
        for(Player player: Bukkit.getOnlinePlayers()) {
            player.setScoreboard(mainScoreboard);
        }
    }




    public void setPlayersRedName(List<GamePlayer> gamePlayers) {
        Team team = mainScoreboard.getTeam("Loup-Garous");
        if (team == null) {
            team = this.mainScoreboard.registerNewTeam("Loup-Garous");
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
            team.setColor(ChatColor.RED);
        }
        for(GamePlayer player: gamePlayers) {
            team.addEntry(player.getPlayer().getName());
            player.getPlayer().setScoreboard(this.mainScoreboard);
            player.getPlayer().setDisplayName(ChatColor.WHITE + player.getPlayer().getName());
        }
    }

    public void addVoteTest(Player player, int nb) {
        String objectiveName = player.getName() + "_werewolves_votes";
        Objective objective = this.mainScoreboard.getObjective(objectiveName);

        if (objective == null) {
            objective = this.mainScoreboard.registerNewObjective(objectiveName, "dummy", ChatColor.GOLD + "Votes");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }
        for (String entry : this.mainScoreboard.getEntries()) {
            Score scoreEntry = objective.getScore(entry);
            scoreEntry.setScore(nb);
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.setScoreboard(mainScoreboard);
        }
    }





    public void resetPlayerNames() {
        for (Team team : mainScoreboard.getTeams()) { //TODO : On ne peut pas supprimer toutes les teams (composition)
            team.unregister();
        }
    }
}
