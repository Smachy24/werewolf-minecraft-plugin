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
        // Créer un comparateur personnalisé
        Comparator<Role> roleComparator = Comparator
                .comparing(Role::getTeam)  // Trier par camp
                .thenComparing(Role::getFrenchName);  // En cas d'égalité, trier par ordre alphabétique du nom

        // Utiliser Collections.sort avec le comparateur
        Collections.sort(gameConfigRoles, roleComparator);
    }

    public static void setScoreBoard(Player player) {
        int i = 99;
        orderRoles();
        for (Role role : gameConfigRoles) {
            String roleName = role.getFrenchName();
            int roleCount = Collections.frequency(gameConfigRoles, role);
            ChatColor roleColor = getRoleColor(role);

            Bukkit.getServer().broadcastMessage(roleColor + roleName + ": " + roleCount);

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

        orderRoles();
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



//    public static void updateScoreBoard(Player player) {
//        if (gameConfigRoles.isEmpty()) {
//            return;
//        }
//
//        Role removedRole = gameConfigRoles.remove(0); // Remove the first role
//
//        String roleName = removedRole.getFrenchName();
//        int roleCount = Collections.frequency(gameConfigRoles, removedRole);
//        boolean roleExists = gameConfigRoles.contains(removedRole);
//
//        Team composition = obj.getScoreboard().getTeam(roleName);
//        if (composition == null) {
//            composition = obj.getScoreboard().registerNewTeam(roleName);
//        }
//
////        for (String entry : composition.getEntries()) {
////            obj.getScoreboard().resetScores(entry);
////        }
//
//        if (!roleExists) {
//            composition.addEntry(ChatColor.GRAY + roleName);
////            obj.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GRAY + roleName).setScore(roleCount);
//            Bukkit.getServer().broadcastMessage("ROLE INEXISTANT");
//        } else {
//            composition.addEntry(roleName);
////            obj.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(roleName).setScore(roleCount);
//        }
//        player.setScoreboard(board);
//    }

}
