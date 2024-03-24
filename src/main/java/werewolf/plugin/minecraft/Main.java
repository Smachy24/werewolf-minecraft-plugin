package werewolf.plugin.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import werewolf.plugin.minecraft.commands.*;
import werewolf.plugin.minecraft.commands.WerewolvesCommand.WerewolvesCommandTabCompleter;
import werewolf.plugin.minecraft.menus.ConfigGui;
import werewolf.plugin.minecraft.scoreboards.WerewolvesPlayersObjective;


public final class Main extends JavaPlugin {

    private static Main instance;

    private ConfigGui configGui;

    public Scoreboard getMainScoreboard() {
        return Bukkit.getScoreboardManager().getMainScoreboard();
    }

    @Override
    public void onEnable() {
        System.out.println("Werewolf plugin has started");
        instance = this;
        configGui = new ConfigGui();

        getCommand("start").setExecutor(new StartCommand());
        getCommand("role").setExecutor(new ShowRoleCommand());
        getCommand("test-remove-role").setExecutor(new TestRemoveRole());
        getCommand("config").setExecutor(new ConfigCommand(this));
        getCommand("lg").setExecutor(new WerewolvesCommand());
        getCommand("lg").setTabCompleter(new WerewolvesCommandTabCompleter());
        Bukkit.getPluginManager().registerEvents(configGui, this);
    }

    @Override
    public void onDisable() {
        System.out.println("Werewolf plugin has stopped");
    }

    public static Main getInstance() {
        return instance;
    }
}
