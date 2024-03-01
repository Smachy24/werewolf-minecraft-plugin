package werewolf.plugin.minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            if(s.equalsIgnoreCase("start")) {
                Bukkit.getServer().broadcastMessage("La partie se lance");
                return true;
            }
        }
        return false;
    }
}
