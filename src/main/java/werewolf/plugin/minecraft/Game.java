package werewolf.plugin.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import werewolf.plugin.minecraft.roles.Role;

import java.util.*;

public class Game {

    List<Role> gameRoles;
    List<GamePlayer> gamePlayers;

    public Game(List<Role> roles) {
        this.gameRoles = roles;
        this.gamePlayers = new ArrayList<>();
    }

    public void playGame() {
        startGame();
    }

    private void startGame() {
        assignRoleToEachPlayer();
    }

    private void assignRoleToEachPlayer() {
        List<Role> tempRoles = new ArrayList<>(gameRoles);
        Random rand = new Random();
        for(Player player: Bukkit.getOnlinePlayers()) {
            Role randomRole = tempRoles.get(rand.nextInt(tempRoles.size()));
            assignRole(player, randomRole);
            tempRoles.remove(randomRole);
        }
    }

    private void assignRole(Player player, Role role) {
        GamePlayer gamePlayer = new GamePlayer(player, role);
        gamePlayers.add(gamePlayer);
        gamePlayer.showRole();
    }

}
