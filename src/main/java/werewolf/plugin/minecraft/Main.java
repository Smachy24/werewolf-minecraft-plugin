package werewolf.plugin.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.commands.TestRemoveRole;
import werewolf.plugin.minecraft.scoreboards.CompositionScoreboard;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Werewolf plugin has started");
        getCommand("start").setExecutor(new StartCommand());
        getCommand("test-remove-role").setExecutor(new TestRemoveRole());
    }

    @Override
    public void onDisable() {
        System.out.println("Werewolf plugin has stopped");
    }
}
