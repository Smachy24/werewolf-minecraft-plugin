package werewolf.plugin.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import werewolf.plugin.minecraft.commands.ConfigCommand;
import werewolf.plugin.minecraft.commands.ShowRoleCommand;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.commands.TestRemoveRole;
import werewolf.plugin.minecraft.menus.ConfigGui;


public final class Main extends JavaPlugin {

    private ConfigGui configGui;

    @Override
    public void onEnable() {
        System.out.println("Werewolf plugin has started");
        configGui = new ConfigGui();

        getCommand("start").setExecutor(new StartCommand());
        getCommand("role").setExecutor(new ShowRoleCommand());
        getCommand("test-remove-role").setExecutor(new TestRemoveRole());
        getCommand("config").setExecutor(new ConfigCommand(this));
        Bukkit.getPluginManager().registerEvents(configGui, this);
    }

    @Override
    public void onDisable() {
        System.out.println("Werewolf plugin has stopped");
    }
}
