package werewolf.plugin.minecraft.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import werewolf.plugin.minecraft.GameConfiguration;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.roles.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CompositionObjective {

    private final Scoreboard mainScoreboard;
    private final Objective compositionObjective;
    private final ArrayList<Role> gameConfigRoles = GameConfiguration.getInstance().getGameRoles();

    public CompositionObjective() {
        this.mainScoreboard = Main.getInstance().getMainScoreboard();
        this.compositionObjective = this.createObjective();
    }

    private Objective createObjective() {
        Objective obj = this.mainScoreboard.registerNewObjective("Composition", "dummy", "§5Composition");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        return obj;
    }


    private static ChatColor getRoleColor(Role role){
        if(role.getTeam().equalsIgnoreCase("Villagers")){
            return ChatColor.GREEN;
        } else if (role.getTeam().equalsIgnoreCase("Werewolves")) {
            return ChatColor.RED;
        }
        else if (role.getTeam().equalsIgnoreCase("Neutral")){
            return ChatColor.YELLOW;
        }
        else {
            return ChatColor.WHITE;
        }
    }

    private void orderRoles() {
        Comparator<Role> roleComparator = Comparator
                .comparing(Role::getTeam)
                .thenComparing(Role::getFrenchName);
        Collections.sort(gameConfigRoles, roleComparator);
    }

    public void setScoreBoard(Player player) {
        int i = 99;
        orderRoles();
        for (Role role : gameConfigRoles) {
            String roleName = role.getFrenchName();
            int roleCount = Collections.frequency(gameConfigRoles, role);
            ChatColor roleColor = getRoleColor(role);

            Team composition = this.compositionObjective.getScoreboard().getTeam(roleName);
            if (composition == null) {
                composition = this.compositionObjective.getScoreboard().registerNewTeam(roleName);
                i--;
            }

            composition.setColor(roleColor);
            composition.addEntry(roleName);
            composition.setSuffix(ChatColor.BLUE+ " - " +  roleCount);
            this.compositionObjective.getScore(roleName).setScore(i);
        }
        player.setScoreboard(this.mainScoreboard);
    }

    public void updateScoreBoard(Player player) {
        if (gameConfigRoles.isEmpty()) {
            return;
        }

        Role firstRole = gameConfigRoles.remove(0); // TODO: Remove correct role not first
        String roleName = firstRole.getFrenchName();
        int roleCount = Collections.frequency(gameConfigRoles, firstRole);

        ChatColor roleColor = (roleCount > 0) ? getRoleColor(firstRole) : ChatColor.GRAY;
        ChatColor suffixColor = (roleCount > 0) ? ChatColor.BLUE : ChatColor.GRAY;

        Team composition = this.compositionObjective.getScoreboard().getTeam(roleName);
        if (composition == null) {
            composition = this.compositionObjective.getScoreboard().registerNewTeam(roleName);
        }
        composition.setColor(roleColor);
        composition.setSuffix(suffixColor + " - " + roleCount);
        player.setScoreboard(this.mainScoreboard);
    }
}
