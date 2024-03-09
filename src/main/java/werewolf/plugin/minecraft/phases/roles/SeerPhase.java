package werewolf.plugin.minecraft.phases.roles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.menus.SeerGui;
import werewolf.plugin.minecraft.phases.NightPhase;
import werewolf.plugin.minecraft.utils.Title;

import java.util.List;
import java.util.Properties;

public class SeerPhase extends Phase{

    private int duration;
    private Inventory inventory;
    private static List<GamePlayer> players;
    private BukkitRunnable phaseRunnable;

    private static boolean phaseTerminated;

    public static boolean isPhaseTerminated() {
        return phaseTerminated;
    }

    public static void setPhaseTerminated(boolean phaseTerminated) {
        SeerPhase.phaseTerminated = phaseTerminated;
    }

    public SeerPhase(){
        this.setProperties();
    }

    @Override
    public String getPhaseName() {
        return "seer";
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void showInventory(){
        for(GamePlayer gamePlayer: players) {
            List<GamePlayer> otherAliveGamePlayer = StartCommand.game.getOtherAliveGamePlayer(gamePlayer);
            inventory = SeerGui.createInventorySeer(otherAliveGamePlayer);
            gamePlayer.getPlayer().openInventory(inventory);

        }
    }

    @Override
    public void setProperties() {
        Properties props = createFromProperties(getPhaseName());
        String durationKey = getPhaseName() + ".duration";

        if (props.containsKey(durationKey)) {
            this.duration = Integer.parseInt(props.getProperty(durationKey));
        } else {
            throw new RuntimeException("Missing duration property for phase: " + getPhaseName());
        }
    }

    @Override
    public void phaseEngine() {
        phaseTerminated = true;
        this.players = StartCommand.getCurrentGame().getGamePlayersByRoleName("Seer");
        Title.sendTitleToEveryone(ChatColor.GREEN + players.get(0).getRole().getFrenchName(),
                ChatColor.BLUE + "C'est à vous !");
        new BukkitRunnable() {
            @Override
            public void run() {
                showInventory();
            }
        }.runTaskLater(Main.getInstance(), 100L);

        this.phaseRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                endPhase();
                this.cancel();
            }
        };
        this.phaseRunnable.runTaskLater(Main.getInstance(), duration*20L);
    }


    public static void stopPhaseEngine() {
        if (NightPhase.task != null && !NightPhase.task.isCancelled()) {
            NightPhase.task.cancel();
        }
        for(GamePlayer gamePlayer: StartCommand.getCurrentGame().getAliveGamePlayer()) {
            gamePlayer.getPlayer().setLevel(0);
        }
        endPhase();
    }

    public static void endPhase() {
         if(phaseTerminated) {
             Title.sendTitleToEveryone(ChatColor.GREEN + players.get(0).getRole().getFrenchName(),
                     ChatColor.BLUE + "Au dodo !");
         }
         phaseTerminated = false;
    }
}
