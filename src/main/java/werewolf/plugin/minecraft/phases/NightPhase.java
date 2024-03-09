package werewolf.plugin.minecraft.phases;

import werewolf.plugin.minecraft.phases.roles.Phase;
import werewolf.plugin.minecraft.phases.roles.SeerPhase;

import java.util.ArrayList;
import java.util.List;

public class NightPhase extends Phase{

    private List<Phase> phases = new ArrayList<>();

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
        this.phases.add(new SeerPhase());
    }

    @Override
    public void endPhase() {

    }
}
