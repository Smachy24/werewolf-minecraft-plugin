package werewolf.plugin.minecraft.phases;

import werewolf.plugin.minecraft.phases.ElectionPhase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class Phase {

    public static Properties createFromProperties(String primaryProp) {
        Properties props = new Properties();
        InputStream input = ElectionPhase.class.getClassLoader().getResourceAsStream("game.properties");
        try {
            props.load((input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Properties resultProps = new Properties();
        props.forEach((key, value) -> {
            if (key.toString().startsWith(primaryProp)) {
                resultProps.put(key, value);
            }
        });
        return resultProps;
    }
    public abstract String getPhaseName();

    public abstract void setProperties();

    public abstract void startPhaseEngine();

    public abstract void stopPhaseEngine();
}
