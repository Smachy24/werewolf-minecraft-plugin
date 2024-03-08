package werewolf.plugin.minecraft.phases.roles;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.menus.SeerGui;

import java.util.List;
import java.util.Properties;

public class SeerPhase extends Phase{
    private int duration;
    private List<GamePlayer> players;

    public SeerPhase(){
        this.setProperties();
        this.phaseEngine();
    }

    @Override
    public String getPhaseName() {
        return "seer";
    }

    @Override
    public void setProperties() {
        Properties props = createFromProperties(getPhaseName());
        String durationKey = getPhaseName() + ".duration";

        if (props.containsKey(durationKey)) {
            this.duration = Integer.parseInt(props.getProperty(durationKey));
        } else {
            throw new RuntimeException("Missing duration property for phase: " + getPhaseName());
        }
    }

    @Override
    public void phaseEngine() {
        this.players = StartCommand.getCurrentGame().getGamePlayersByRoleName("Seer");

        for(GamePlayer gamePlayer: this.players) {
            List<GamePlayer> otherAliveGamePlayer = StartCommand.game.getOtherAliveGamePlayer(gamePlayer);
            Inventory inventory= SeerGui.createInventorySeer(otherAliveGamePlayer);
            gamePlayer.getPlayer().openInventory(inventory);
        }
    }

}
