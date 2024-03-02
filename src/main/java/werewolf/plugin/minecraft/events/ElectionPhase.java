package werewolf.plugin.minecraft.events;

import java.util.Properties;

public class ElectionPhase extends Phase{

    int duration;

    public ElectionPhase(){
        this.setProperties();
    }

    @Override
    public void setProperties() {
        Properties props = Phase.createFromProperties("election");
        this.duration = Integer.parseInt(props.getProperty("duration")); //election.duration ?
    }
}
