package werewolf.plugin.minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.menus.SeerGui;

import java.util.List;

public class TestSeerSpectateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            if(s.equalsIgnoreCase("spectate")){
                Player player = (Player) commandSender;
                GamePlayer gamePlayer = StartCommand.game.getGamePlayerByPlayer(player);
                if(gamePlayer.getRole().getName().equalsIgnoreCase("seer")) {
                    List<GamePlayer> otherAliveGamePlayer = StartCommand.game.getOtherAliveGamePlayer(gamePlayer);
                    Inventory inventory= SeerGui.createInventorySeer(otherAliveGamePlayer);
                    player.openInventory(inventory);
                    return true;
                }
                else {
                    player.sendMessage(ChatColor.RED + "Vous n'êtes pas la voyante");
                    return true;
                }
            }
        }
        return false;
    }

}
