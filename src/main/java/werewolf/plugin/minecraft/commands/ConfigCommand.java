package werewolf.plugin.minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import werewolf.plugin.minecraft.utils.RolesConfiguration;

public class ConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            if(s.equalsIgnoreCase("config")){

                RolesConfiguration config = new RolesConfiguration();
                System.out.println(config.getConfigRoles());

                return true;
            }
        }
        return false;
    }
}