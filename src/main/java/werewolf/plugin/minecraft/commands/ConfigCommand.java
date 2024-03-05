package werewolf.plugin.minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;  // Import the Listener interface
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.utils.RolesConfiguration;
import werewolf.plugin.minecraft.events.ConfigGui;

public class ConfigCommand implements CommandExecutor, Listener {

    private final Main plugin;

    public ConfigCommand(Main plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (s.equalsIgnoreCase("config")) {

                Bukkit.getPluginManager().registerEvents(new ConfigGui(), plugin);  // Use the correct listener instance
                RolesConfiguration.getItemConfig((Player) commandSender);

                return true;
            }
        }
        return false;
    }
}
