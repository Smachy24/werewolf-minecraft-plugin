package werewolf.plugin.minecraft.phases;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.phases.roles.Phase;
import werewolf.plugin.minecraft.phases.roles.SeerPhase;

import java.util.ArrayList;
import java.util.List;

public class NightPhase extends Phase{

    private List<Phase> phases = new ArrayList<>();
    public static BukkitTask task;
    private int count;

    public NightPhase() {
        this.phaseEngine();
    }


    @Override
    public String getPhaseName() {
        return null;
    }

    @Override
    public void setProperties() {

    }

    @Override
    public void phaseEngine() {
        SeerPhase seerPhase = new SeerPhase();
        this.count = seerPhase.getDuration();
        seerPhase.phaseEngine();
        // Timer
        task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), ()-> {
            for(GamePlayer gamePlayer: StartCommand.getCurrentGame().getAliveGamePlayer()) {
                gamePlayer.getPlayer().setLevel(count);
            }
            if(count == 0) {
                seerPhase.stopPhaseEngine();
                task.cancel();
            }
            count --;
            }, 20, 20);
    }

}
