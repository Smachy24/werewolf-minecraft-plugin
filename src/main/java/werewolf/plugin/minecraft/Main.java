package werewolf.plugin.minecraft;

import org.bukkit.plugin.java.JavaPlugin;
import werewolf.plugin.minecraft.commands.StartCommand;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Werewolf plugin has started");
        getCommand("start").setExecutor(new StartCommand());
    }

    @Override
    public void onDisable() {
        System.out.println("Werewolf plugin has stopped");
    }
}
