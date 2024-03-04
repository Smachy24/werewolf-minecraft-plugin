package werewolf.plugin.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import werewolf.plugin.minecraft.commands.ConfigCommand;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.commands.TestRemoveRole;
import werewolf.plugin.minecraft.events.ConfigGui;


public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Werewolf plugin has started");
        getCommand("start").setExecutor(new StartCommand());
        getCommand("test-remove-role").setExecutor(new TestRemoveRole());
        getCommand("config").setExecutor(new ConfigCommand(this));
        Bukkit.getPluginManager().registerEvents(new ConfigGui(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("Werewolf plugin has stopped");
    }
}
