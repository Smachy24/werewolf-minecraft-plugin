package werewolf.plugin.minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.phases.Phase;
import werewolf.plugin.minecraft.phases.roles.WerewolvesPhase;
import werewolf.plugin.minecraft.scoreboards.WerewolvesPlayersObjective;

import java.util.ArrayList;
import java.util.List;

public class WerewolvesCommand implements CommandExecutor {
    private final String objectiveSuffix = "_werewolves_votes";
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(s.equalsIgnoreCase("lg")) {
                if(StartCommand.getCurrentGame()!=null) {
                    GamePlayer currentGamePlayer = StartCommand.getCurrentGame().getGamePlayerByPlayer(player);
                    if(currentGamePlayer.getRole().getTeam().equalsIgnoreCase("Werewolves")) {
                        if(strings.length > 0) {

                            if(strings[0].equalsIgnoreCase("chat")) {
                                chatAction(strings, player);
                                return true;
                            } else if (strings[0].equalsIgnoreCase("kill")) {
                                if(strings.length == 2) {
                                    String playerName = strings[1];
                                    Scoreboard mainScoreboard = Main.getInstance().getMainScoreboard();
                                    Objective obj = mainScoreboard.getObjective(playerName + objectiveSuffix);
                                    if(obj == null) {
                                        player.sendMessage(ChatColor.RED + "Erreur : mauvaise commande (/lg kill pseudo)");
                                        return true;
                                    }
                                    Player votedPlayer = Bukkit.getPlayer(playerName);
                                    Phase currentPhase = StartCommand.getCurrentGame().getCurrentPhase();
                                    if(currentPhase instanceof WerewolvesPhase) {
                                        WerewolvesPhase werewolvesPhase = (WerewolvesPhase) currentPhase;
                                        if(werewolvesPhase.werewolfHasVoted(player)) {
                                            werewolvesPhase.removeVote(player);
                                        }
                                        werewolvesPhase.addVote(player, votedPlayer);
                                        werewolvesPhase.getWerewolvesPlayersObjective().updateScoreboard();
                                        return true;
                                    }
                                    else {
                                        player.sendMessage(ChatColor.RED + "Ce n'est pas le moment de voter !");
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "Erreur : mauvaise commande (/lg kill pseudo)");
                                    return true;
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Cette commande n'existe pas");
                                return true;
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Attention le message est vide");
                            return true;
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Vous n'êtes pas loup-garou, vous ne pouvez pas utilise cette commande");
                        return true;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "La partie n'a pas commencé");
                    return true;
                }
            }
        }
        return false;
    }

    private void chatAction(String[] strings, Player player) {
        String playerMessage = String.join(" ", strings);
        List<GamePlayer> gamePlayers = StartCommand.getCurrentGame().getGamePlayersByRoleName("Werewolf");
        for(GamePlayer gamePlayer: gamePlayers) {
            gamePlayer.getPlayer().sendMessage(ChatColor.RED + "<"+player.getName()+"> " + ChatColor.WHITE +  playerMessage);
        }
    }

    public static class WerewolvesCommandTabCompleter implements org.bukkit.command.TabCompleter {
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            if (command.getName().equalsIgnoreCase("lg")) {
                if (args.length == 1) {
                    List<String> completions = new ArrayList<>();
                    completions.add("chat");
                    completions.add("kill");
                    return completions;
                }
            }
            return null;
        }
    }
}
