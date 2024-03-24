package werewolf.plugin.minecraft.phases.roles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.menus.SeerGui;
import werewolf.plugin.minecraft.phases.Phase;
import werewolf.plugin.minecraft.scoreboards.WerewolvesPlayersObjective;
import werewolf.plugin.minecraft.utils.Title;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WerewolvesPhase extends Phase {

    private int duration;
    private Map<GamePlayer, SeerGui> gameWerewolves; // All werewolves in current game


    private WerewolvesPlayersObjective werewolvesPlayersObjective;

    private Map<Player, Player> phaseVotedPlayers;

    public WerewolvesPhase() {
        this.setProperties();
        this.phaseVotedPlayers = new HashMap<>();
        this.werewolvesPlayersObjective = new WerewolvesPlayersObjective(this);
    }

    public WerewolvesPlayersObjective getWerewolvesPlayersObjective() {
        return werewolvesPlayersObjective;
    }

    public Map<Player, Player> getPhaseVotedPlayers() {
        return phaseVotedPlayers;
    }

    public int getPhaseVotesCountByPlayer(Player player) {
        int countVotes = 0;
        for (Map.Entry<Player, Player> entry : phaseVotedPlayers.entrySet()) {
            if (entry.getValue().equals(player)) {
                countVotes++;
            }
        }
        return countVotes;
    }

    public boolean werewolfHasVoted(Player werewolf) {
        return phaseVotedPlayers.containsKey(werewolf);
    }


    public void addVote(Player werewolf, Player votedPlayer) {
        Bukkit.broadcastMessage(werewolf.getName() + " -> " + votedPlayer.getName());
        phaseVotedPlayers.put(werewolf, votedPlayer);
    }

    public void removeVote(Player werewolf) {
        phaseVotedPlayers.remove(werewolf);
    }


    @Override
    public String getPhaseName() {
        return "werewolves";
    }

    @Override
    public void setProperties() {
        this.gameWerewolves = new HashMap<>();
        Properties props = createFromProperties(getPhaseName());
        String durationKey = getPhaseName() + ".duration";

        if (props.containsKey(durationKey)) {
            this.duration = Integer.parseInt(props.getProperty(durationKey));
        } else {
            throw new RuntimeException("Missing duration property for phase: " + getPhaseName());
        }
    }

    @Override
    public void startPhaseEngine() {
        this.werewolvesPlayersObjective.resetAllObjectives();
        List<GamePlayer> players = StartCommand.getCurrentGame().getGamePlayersByRoleName("Werewolf");
        Title.sendTitleToEveryone(ChatColor.RED + players.get(0).getRole().getFrenchName(),
                ChatColor.BLUE + "C'est à vous !");
//        addColorWerewolves();
        this.werewolvesPlayersObjective.initAllObjectives();
    }

    @Override
    public void stopPhaseEngine() {

    }

    private void addColorWerewolves() {
//        WerewolvesPlayersObjective scoreboard = Main.getInstance().werewolvesScoreboard;
        List<GamePlayer> gameWerewolves = StartCommand.getCurrentGame().getGamePlayersByTeamName("Werewolves");
//        scoreboard.setPlayersRedName(gameWerewolves);
    }
}
