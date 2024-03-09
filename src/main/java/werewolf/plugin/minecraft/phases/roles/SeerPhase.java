package werewolf.plugin.minecraft.phases.roles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.menus.SeerGui;
import werewolf.plugin.minecraft.phases.NightPhase;
import werewolf.plugin.minecraft.utils.Title;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SeerPhase extends Phase{

    private int duration;
    private Map<GamePlayer, SeerGui> gameSeers = new HashMap<>(); // All seers in current game

    private boolean phaseTerminated;

    public SeerPhase(){
        this.setProperties();
    }

    public boolean isPhaseTerminated() {
        return phaseTerminated;
    }

    public void setPhaseTerminated(boolean phaseTerminated) {
        this.phaseTerminated = phaseTerminated;
    }

    public void checkIfPhaseTerminated() {
        boolean t = false;
        for(Map.Entry<GamePlayer, SeerGui> seer: gameSeers.entrySet()) {
            Player player = seer.getKey().getPlayer();
            player.sendMessage(player.getName() + " : " + seer.getValue().isChoiceValidated());
            if(seer.getValue().isChoiceValidated()) {
                player.sendMessage("BB");
                t = true;
            }
            else {
                player.sendMessage("CC");
                return;
            }
        }
        if(t) {
            Bukkit.broadcastMessage("DD");
            this.stopPhaseEngine();
        }
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
        for(Map.Entry<GamePlayer, SeerGui> seer: gameSeers.entrySet()) {
            List<GamePlayer> otherAliveGamePlayer = StartCommand.game.getOtherAliveGamePlayer(seer.getKey());
            Inventory inventory = seer.getValue().createInventorySeer(otherAliveGamePlayer);
            seer.getKey().getPlayer().openInventory(inventory);

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
        // Get all seer GamePlayer and set to HashMap
        List<GamePlayer> players = StartCommand.getCurrentGame().getGamePlayersByRoleName("Seer");
        for(GamePlayer player: players) {
            SeerGui seerGui = new SeerGui(this, player);
            Bukkit.getPluginManager().registerEvents(seerGui, Main.getInstance());
            gameSeers.put(player, seerGui);
        }

        Title.sendTitleToEveryone(ChatColor.GREEN + players.get(0).getRole().getFrenchName(),
                ChatColor.BLUE + "C'est à vous !");
        new BukkitRunnable() {
            @Override
            public void run() {
                showInventory();
            }
        }.runTaskLater(Main.getInstance(), 100L);

        new BukkitRunnable() {
            @Override
            public void run() {
                endPhase();
                this.cancel();
            }
        }.runTaskLater(Main.getInstance(), duration*20L);
    }


    public void stopPhaseEngine() {
        if (NightPhase.task != null && !NightPhase.task.isCancelled()) {
            NightPhase.task.cancel();
        }
        for(GamePlayer gamePlayer: StartCommand.getCurrentGame().getAliveGamePlayer()) {
            gamePlayer.getPlayer().setLevel(0);
        }
        endPhase();
    }

    public void endPhase() {
         if(phaseTerminated) {
             for(Map.Entry<GamePlayer, SeerGui> seer: gameSeers.entrySet()){
                 Title.sendTitleToEveryone(ChatColor.GREEN + seer.getKey().getRole().getFrenchName(),
                 ChatColor.BLUE + "Au dodo !");
                 break;
             }

         }
         phaseTerminated = false;
    }
}
