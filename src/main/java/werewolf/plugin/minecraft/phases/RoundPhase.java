package werewolf.plugin.minecraft.phases;

import org.bukkit.Bukkit;
import werewolf.plugin.minecraft.phases.roles.Phase;
import werewolf.plugin.minecraft.phases.roles.SeerPhase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoundPhase {

    private  int currentRound;
    private List<Phase> phases = new ArrayList<>();

    public RoundPhase(int round) {
        this.currentRound = round;
        this.phases.add(new SeerPhase());
    }


}
