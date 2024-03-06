package werewolf.plugin.minecraft;

import werewolf.plugin.minecraft.roles.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameConfiguration {

    private static ArrayList<Role> gameRoles = new ArrayList<>();
    private static GameConfiguration instance;

    public static GameConfiguration getInstance() {
        if (instance == null) {
            instance = new GameConfiguration();
        }
        return instance;
    }

    public ArrayList<Role> getGameRoles() {
        return gameRoles;
    }

    public List<Role> getVillagerRoles() {
        return filterRolesByTeam("Villagers");
    }

    public List<Role> getWerewolvesRoles() {
        return filterRolesByTeam("Werewolves");
    }

    public List<Role> getNeutralRoles() {
        return filterRolesByTeam("Neutral");
    }

    private List<Role> filterRolesByTeam(String team) {
        return gameRoles.stream()
                .filter(role -> role.getTeam().equalsIgnoreCase(team))
                .collect(Collectors.toList());
    }

    public void setGameRoles(ArrayList<Role> gameRoles) {
        GameConfiguration.gameRoles = gameRoles;
    }

    public boolean containsRole(Role role) {
        return gameRoles.contains(role);
    }

    public void addRole(Role role) {
        gameRoles.add(role);
    }

    public void removeRole(Role role) {
        gameRoles.remove(role);
    }
}
