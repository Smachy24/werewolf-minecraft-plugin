package werewolf.plugin.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import werewolf.plugin.minecraft.commands.*;
import werewolf.plugin.minecraft.menus.ConfigGui;
import werewolf.plugin.minecraft.menus.SeerGui;


public final class Main extends JavaPlugin {

    private ConfigGui configGui;
    private SeerGui seerGui;

    @Override
    public void onEnable() {
        System.out.println("Werewolf plugin has started");
        configGui = new ConfigGui();
        seerGui = new SeerGui();

        getCommand("start").setExecutor(new StartCommand());
        getCommand("role").setExecutor(new ShowRoleCommand());
        getCommand("spectate").setExecutor(new TestSeerSpectateCommand());
        getCommand("test-remove-role").setExecutor(new TestRemoveRole());
        getCommand("config").setExecutor(new ConfigCommand(this));
        Bukkit.getPluginManager().registerEvents(configGui, this);
        Bukkit.getPluginManager().registerEvents(seerGui, this);
    }

    @Override
    public void onDisable() {
        System.out.println("Werewolf plugin has stopped");
    }
}
