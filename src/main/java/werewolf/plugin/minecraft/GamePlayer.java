package werewolf.plugin.minecraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import werewolf.plugin.minecraft.roles.Role;

public class GamePlayer {

    ChatColor colorMessage = ChatColor.BLUE;

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
        this.player.sendMessage(colorMessage + "Vous êtes " + role.getColor() + role.getFrenchName() + "\n" +
                colorMessage + role.getDescription() );
    }



}
