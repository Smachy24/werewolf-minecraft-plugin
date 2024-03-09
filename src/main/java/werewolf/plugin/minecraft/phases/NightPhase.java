package werewolf.plugin.minecraft.phases;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import werewolf.plugin.minecraft.Game;
import werewolf.plugin.minecraft.GameConfiguration;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.phases.roles.Phase;
import werewolf.plugin.minecraft.phases.roles.SeerPhase;
import werewolf.plugin.minecraft.utils.Experience;

import java.util.ArrayList;
import java.util.List;

public class NightPhase extends Phase{

    private List<Phase> phases = new ArrayList<>();
    private BukkitTask task;
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
        count = seerPhase.getDuration();
        seerPhase.phaseEngine();
        task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), ()-> {
            for(GamePlayer gamePlayer: StartCommand.getCurrentGame().getAliveGamePlayer()) {
                gamePlayer.getPlayer().setLevel(count);
//                Experience.changeExp(gamePlayer.getPlayer(), Experience.getExpFromLevel(count));
            }
            if(count == 0) {
                task.cancel();
            }
            count --;
            }, 20, 20);
    }

    @Override
    public void endPhase() {

    }
}
