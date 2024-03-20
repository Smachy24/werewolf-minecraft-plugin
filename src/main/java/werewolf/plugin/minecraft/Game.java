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

    public List<Role> getGameRoles() {
        return gameRoles;
    }

    public void setGameRoles(List<Role> gameRoles) {
        this.gameRoles = gameRoles;
    }

    public List<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(List<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public GamePlayer getGamePlayerByPlayer(Player player) {
        for (GamePlayer gamePlayer : gamePlayers) {
            if (gamePlayer.getPlayer().equals(player)) {
                return gamePlayer;
            }
        }
        return null;
    }
    public List<GamePlayer> getGamePlayersByRoleName(String roleName) {
        List<GamePlayer> tempPlayers = new ArrayList<>();
        for (GamePlayer gamePlayer : gamePlayers) {
            if (gamePlayer.getRole().getName().equalsIgnoreCase(roleName)) {
                tempPlayers.add(gamePlayer);
            }
        }
        return tempPlayers;
    }

    public List<GamePlayer> getGamePlayersByTeamName(String teamName) {
        List<GamePlayer> tempPlayers = new ArrayList<>();
        for (GamePlayer gamePlayer : gamePlayers) {
            if (gamePlayer.getRole().getTeam().equalsIgnoreCase(teamName)) {
                tempPlayers.add(gamePlayer);
            }
        }
        return tempPlayers;
    }


    public GamePlayer getGamePlayerByPlayerName(String playerName) {
        for (GamePlayer gamePlayer : gamePlayers) {
            if (gamePlayer.getPlayer().getName().equals(playerName)) {
                return gamePlayer;
            }
        }
        return null;
    }

    public List<GamePlayer> getAliveGamePlayer() {
        List<GamePlayer> tempPlayers = new ArrayList<>(gamePlayers);
        for(GamePlayer gamePlayer: gamePlayers) {
            if(gamePlayer.getPlayer().getGameMode() != GameMode.SURVIVAL) {
                tempPlayers.remove(gamePlayer);
            }
        }
        return tempPlayers;
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

    public void startGame() {
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
