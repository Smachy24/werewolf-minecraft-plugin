package werewolf.plugin.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import werewolf.plugin.minecraft.commands.*;
import werewolf.plugin.minecraft.menus.ConfigGui;
import werewolf.plugin.minecraft.menus.SeerGui;
import werewolf.plugin.minecraft.scoreboards.WerewolvesPlayersScoreboard;


public final class Main extends JavaPlugin {

    private static Main instance;

    public WerewolvesPlayersScoreboard werewolvesScoreboard;
    private ConfigGui configGui;


    @Override
    public void onEnable() {
        System.out.println("Werewolf plugin has started");
        instance = this;
        configGui = new ConfigGui();

        this.werewolvesScoreboard = new WerewolvesPlayersScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

        getCommand("start").setExecutor(new StartCommand());
        getCommand("role").setExecutor(new ShowRoleCommand());
        getCommand("test-remove-role").setExecutor(new TestRemoveRole());
        getCommand("config").setExecutor(new ConfigCommand(this));
        getCommand("lg-chat").setExecutor(new WerewolfChatCommand());
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
