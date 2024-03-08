package werewolf.plugin.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

    public GamePlayer getGamePlayerByPlayer(Player player) {
        for (GamePlayer gamePlayer : gamePlayers) {
            if (gamePlayer.getPlayer().equals(player)) {
                return gamePlayer;
            }
        }
        return null;
    }

    public GamePlayer getGamePlayerByPlayerName(String playerName) {
        for (GamePlayer gamePlayer : gamePlayers) {
            if (gamePlayer.getPlayer().getName().equals(playerName)) {
                return gamePlayer;
            }
        }
        return null;
    }

    public List<GamePlayer> getOtherAliveGamePlayer(GamePlayer currentPlayer) {
        List<GamePlayer> tempPlayers = new ArrayList<>(gamePlayers);
        for(GamePlayer gamePlayer: gamePlayers) {
            if(gamePlayer == currentPlayer || gamePlayer.getPlayer().getGameMode() != GameMode.SURVIVAL) {
                tempPlayers.remove(gamePlayer);
            }
        }
        return tempPlayers;
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
