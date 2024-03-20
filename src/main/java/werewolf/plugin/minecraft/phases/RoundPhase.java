package werewolf.plugin.minecraft.phases;

import org.bukkit.scheduler.BukkitRunnable;
import werewolf.plugin.minecraft.Main;

import java.util.ArrayList;
import java.util.List;

public class RoundPhase {

    private  int currentRound;
    private List<Phase> phases = new ArrayList<>();

    public RoundPhase(int round) {
        this.currentRound = round;
        roundEngine();
    }

    private void roundEngine() {
        // Wait until role title is finished
        new BukkitRunnable() {

            @Override
            public void run() {
                phases.add(new NightPhase());
            }
        }.runTaskLater(Main.getInstance(), 5*20L);

    }

}
