package werewolf.plugin.minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import werewolf.plugin.minecraft.GameConfiguration;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            if(s.equalsIgnoreCase("start")) {
                Bukkit.getServer().broadcastMessage("La partie se lance");
                GameConfiguration gameConfig = new GameConfiguration();
                gameConfig.createDefaultConfig();
                gameConfig.getGameRoles().forEach(role -> {
                    Bukkit.getServer().broadcastMessage(role.getFrenchName());
                });
                return true;
            }
        }
        return false;
    }
}
