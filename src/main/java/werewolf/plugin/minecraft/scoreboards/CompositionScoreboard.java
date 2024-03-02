package werewolf.plugin.minecraft.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import werewolf.plugin.minecraft.GameConfiguration;
import werewolf.plugin.minecraft.roles.Role;

import java.util.Collections;

public class CompositionScoreboard {

    private static ScoreboardManager manager = Bukkit.getScoreboardManager();
    private static Scoreboard board = manager.getNewScoreboard();
    private static Objective obj = board.registerNewObjective("Composition", "dummy");
    private static GameConfiguration gameConfig = new GameConfiguration();

    static {
        obj.setDisplayName("§eComposition");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public static void setScoreBoard(Player player) {
        for (Role role : gameConfig.getGameRoles()) {
            String roleName = role.getFrenchName();
            int roleCount = Collections.frequency(gameConfig.getGameRoles(), role);

            Bukkit.getServer().broadcastMessage(roleName + ": " + roleCount);

            // Vérifier si l'équipe existe déjà
            Team composition = obj.getScoreboard().getTeam(roleName);
            if (composition == null) {
                composition = obj.getScoreboard().registerNewTeam(roleName);
            }
            composition.addEntry(roleName);
            obj.getScore(roleName).setScore(roleCount);
        }
        player.setScoreboard(board);
    }

    public static void updateScoreBoard(Player player) {
        if (gameConfig.getGameRoles().isEmpty()) {
            return;
        }

        Role removedRole = gameConfig.getGameRoles().remove(0); // Remove the first role

        String roleName = removedRole.getFrenchName();
        int roleCount = Collections.frequency(gameConfig.getGameRoles(), removedRole);
        boolean roleExists = gameConfig.getGameRoles().contains(removedRole);

        Team composition = obj.getScoreboard().getTeam(roleName);
        if (composition == null) {
            composition = obj.getScoreboard().registerNewTeam(roleName);
        }

        for (String entry : composition.getEntries()) {
            obj.getScoreboard().resetScores(entry);
        }

        if (!roleExists) {
            composition.addEntry(ChatColor.GRAY + roleName);
            obj.getScore(ChatColor.GRAY + roleName).setScore(roleCount);
            Bukkit.getServer().broadcastMessage("ROLE INEXISTANT");
        } else {
            composition.addEntry(roleName);
            obj.getScore(roleName).setScore(roleCount);
        }

        player.setScoreboard(board);
    }
}
