package werewolf.plugin.minecraft;

import werewolf.plugin.minecraft.roles.Role;

import java.util.ArrayList;

public class GameConfiguration {

    private ArrayList<Role> gameRoles = new ArrayList<>();
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

    public void setGameRoles(ArrayList<Role> gameRoles) {
        this.gameRoles = gameRoles;
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
