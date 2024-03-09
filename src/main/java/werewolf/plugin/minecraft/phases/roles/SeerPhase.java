package werewolf.plugin.minecraft.phases.roles;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.menus.SeerGui;

import java.util.List;
import java.util.Properties;

public class SeerPhase extends Phase{
    private int duration;
    private Inventory inventory;
    private List<GamePlayer> players;
    private BukkitRunnable phaseRunnable;

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
            this.inventory = SeerGui.createInventorySeer(otherAliveGamePlayer);
            gamePlayer.getPlayer().openInventory(this.inventory);
        }
        this.phaseRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                endPhase();
                this.cancel();
            }
        };
        this.phaseRunnable.runTaskLater(Main.getInstance(), duration*20L);
    }

    @Override
    public void endPhase() {
        for(GamePlayer gamePlayer: this.players) {
            gamePlayer.getPlayer().closeInventory();
        }
    }

}
