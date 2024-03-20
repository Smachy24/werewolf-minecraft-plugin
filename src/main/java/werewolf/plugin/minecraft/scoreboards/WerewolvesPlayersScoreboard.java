package werewolf.plugin.minecraft.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import werewolf.plugin.minecraft.GamePlayer;

import java.util.List;

public class WerewolvesPlayersScoreboard {
    private Scoreboard scoreboard;

    public WerewolvesPlayersScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        this.resetPlayerNames();
    }
    public void setPlayersRedName(List<GamePlayer> gamePlayers) {
        Team team = this.scoreboard.registerNewTeam("Loup-Garous");
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
        team.setColor(ChatColor.RED);
        for(GamePlayer player: gamePlayers) {
            team.addEntry(player.getPlayer().getName());
        }
    }

    public void resetPlayerNames() {
        for (Team team : scoreboard.getTeams()) {
            team.unregister();
        }
    }
}
