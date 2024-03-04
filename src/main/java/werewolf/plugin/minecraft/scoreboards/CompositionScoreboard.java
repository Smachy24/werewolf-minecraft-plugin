package werewolf.plugin.minecraft.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import werewolf.plugin.minecraft.GameConfiguration;
import werewolf.plugin.minecraft.roles.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CompositionScoreboard {

    private static ScoreboardManager manager = Bukkit.getScoreboardManager();
    private static Scoreboard board = manager.getNewScoreboard();
    private static Objective obj = board.registerNewObjective("Composition", "dummy");
    private static ArrayList<Role> gameConfigRoles = new GameConfiguration().getGameRoles();

    static {
        obj.setDisplayName("§5Composition");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
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

    private static void orderRoles() {
        Comparator<Role> roleComparator = Comparator
                .comparing(Role::getTeam)
                .thenComparing(Role::getFrenchName);
        Collections.sort(gameConfigRoles, roleComparator);
    }

    public static void setScoreBoard(Player player) {
        int i = 99;
        orderRoles();
        for (Role role : gameConfigRoles) {
            String roleName = role.getFrenchName();
            int roleCount = Collections.frequency(gameConfigRoles, role);
            ChatColor roleColor = getRoleColor(role);

            Team composition = obj.getScoreboard().getTeam(roleName);
            if (composition == null) {
                composition = obj.getScoreboard().registerNewTeam(roleName);
                i--;
            }

            composition.setColor(roleColor);
            composition.addEntry(roleName);
            composition.setSuffix(ChatColor.BLUE+ " - " +  roleCount);
            obj.getScore(roleName).setScore(i);
        }
        player.setScoreboard(board);
    }

    public static void updateScoreBoard(Player player) {
        if (gameConfigRoles.isEmpty()) {
            return;
        }

        Role firstRole = gameConfigRoles.remove(0); // Retire le premier rôle de la liste
        String roleName = firstRole.getFrenchName();
        int roleCount = Collections.frequency(gameConfigRoles, firstRole);

        ChatColor roleColor = (roleCount > 0) ? getRoleColor(firstRole) : ChatColor.GRAY;
        ChatColor suffixColor = (roleCount > 0) ? ChatColor.BLUE : ChatColor.GRAY;

        Team composition = obj.getScoreboard().getTeam(roleName);
        if (composition == null) {
            composition = obj.getScoreboard().registerNewTeam(roleName);
        }
        composition.setColor(roleColor);
        composition.setSuffix(suffixColor + " - " + roleCount);
        player.setScoreboard(board);
    }
}
