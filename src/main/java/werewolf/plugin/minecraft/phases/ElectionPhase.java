package werewolf.plugin.minecraft.phases;

import java.util.Properties;

public class ElectionPhase extends Phase {

    private int duration;

    public ElectionPhase(){
        this.setProperties();
    }

    @Override
    public String getPhaseName() {
        return "election";
    }

    @Override
    public void setProperties() {
        Properties props = Phase.createFromProperties("election");
        this.duration = Integer.parseInt(props.getProperty("duration")); //election.duration ?
    }

    @Override
    public void startPhaseEngine() {

    }

    @Override
    public void stopPhaseEngine() {

    }

}
