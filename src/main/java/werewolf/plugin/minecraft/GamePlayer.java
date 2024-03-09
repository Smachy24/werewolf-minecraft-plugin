package werewolf.plugin.minecraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import org.bukkit.scheduler.BukkitRunnable;
import werewolf.plugin.minecraft.roles.Role;
import werewolf.plugin.minecraft.utils.Title;

public class GamePlayer {

    private final ChatColor colorMessage = ChatColor.BLUE;

    private Role role;

    private Player player;

    public GamePlayer(Player player, Role role) {
        this.role = role;
        this.player = player;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void showRole() {
        Title.sendTitleToPlayer(this.player, colorMessage + "Vous êtes " + role.getColor() + role.getFrenchName(),
                "");
        this.player.sendMessage(colorMessage + role.getDescription());
    }
}
