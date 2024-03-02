package werewolf.plugin.minecraft;

import werewolf.plugin.minecraft.roles.Role;
import werewolf.plugin.minecraft.roles.Villager;
import werewolf.plugin.minecraft.roles.Werewolf;

import java.util.ArrayList;

public class GameConfiguration {

    public GameConfiguration(){
        createDefaultConfig();
    }
    private ArrayList<Role> gameRoles = new ArrayList<>();

    public ArrayList<Role> getGameRoles() {
        return gameRoles;
    }

    public void setGameRoles(ArrayList<Role> gameRoles) {
        this.gameRoles = gameRoles;
    }

    public void createDefaultConfig() {
        Werewolf werewolf = new Werewolf("Werewolf", "Werewolves", "Loup-garou", "Description d'un loup-garou");
        Villager villager = new Villager("Villager", "Villagers", "Simple villageois", "Description d'un simple villageois");
        this.gameRoles.add(werewolf);
        this.gameRoles.add(werewolf);
        this.gameRoles.add(villager);
        this.gameRoles.add(villager);
    }
}
