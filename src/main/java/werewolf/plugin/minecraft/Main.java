package werewolf.plugin.minecraft;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Werewolf plugin has started");
    }

    @Override
    public void onDisable() {
        System.out.println("Werewolf plugin has stopped");
    }
}
