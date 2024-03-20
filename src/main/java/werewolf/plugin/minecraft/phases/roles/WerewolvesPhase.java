package werewolf.plugin.minecraft.phases.roles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.metadata.FixedMetadataValue;
import org.checkerframework.checker.units.qual.C;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.menus.SeerGui;
import werewolf.plugin.minecraft.phases.Phase;
import werewolf.plugin.minecraft.scoreboards.WerewolvesPlayersScoreboard;
import werewolf.plugin.minecraft.utils.Title;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WerewolvesPhase extends Phase {

    private int duration;
    private Map<GamePlayer, SeerGui> gameWerewolves; // All werewolves in current game

    public WerewolvesPhase() {
        this.setProperties();
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
        List<GamePlayer> players = StartCommand.getCurrentGame().getGamePlayersByRoleName("Werewolf");
        Title.sendTitleToEveryone(ChatColor.RED + players.get(0).getRole().getFrenchName(),
                ChatColor.BLUE + "C'est à vous !");
//        addColorWerewolves();
    }

    @Override
    public void stopPhaseEngine() {

    }

    private void addColorWerewolves() {
        WerewolvesPlayersScoreboard scoreboard = Main.getInstance().werewolvesScoreboard;
        List<GamePlayer> gameWerewolves = StartCommand.getCurrentGame().getGamePlayersByTeamName("Werewolves");
        scoreboard.setPlayersRedName(gameWerewolves);
    }
}
