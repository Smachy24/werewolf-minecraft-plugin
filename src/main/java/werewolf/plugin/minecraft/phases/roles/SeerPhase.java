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
import werewolf.plugin.minecraft.phases.Phase;
import werewolf.plugin.minecraft.utils.Title;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SeerPhase extends Phase {

    private int duration;
    private Map<GamePlayer, SeerGui> gameSeers; // All seers in current game

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

    @Override
    public void setProperties() {
        this.gameSeers = new HashMap<>();
        Properties props = createFromProperties(getPhaseName());
        String durationKey = getPhaseName() + ".duration";

        if (props.containsKey(durationKey)) {
            this.duration = Integer.parseInt(props.getProperty(durationKey));
        } else {
            throw new RuntimeException("Missing duration property for phase: " + getPhaseName());
        }
    }

    @Override
    public void startPhaseEngine() { // start

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
    }

    @Override
    public void stopPhaseEngine() {
        //Cancer count down task
        if (NightPhase.task != null && !NightPhase.task.isCancelled()) {
            NightPhase.task.cancel();
        }
        // Set xp level to all players to 0
        for(GamePlayer gamePlayer: StartCommand.getCurrentGame().getAliveGamePlayer()) {
            gamePlayer.getPlayer().setLevel(0);
        }
        // Close Inventory if not chose
        for(Map.Entry<GamePlayer, SeerGui> seer: gameSeers.entrySet()){
            seer.getValue().setChoiceValidated(true);
            seer.getKey().getPlayer().closeInventory();
        }
        endPhase();
    }

    public void endPhase() {
        // Get current role phase and send message to all players
        for(Map.Entry<GamePlayer, SeerGui> seer: gameSeers.entrySet()){
            Title.sendTitleToEveryone(ChatColor.GREEN + seer.getKey().getRole().getFrenchName(),
                    ChatColor.BLUE + "Au dodo !");
            break;
        }
    }

    public void showInventory(){
        for(Map.Entry<GamePlayer, SeerGui> seer: gameSeers.entrySet()) {
            List<GamePlayer> otherAliveGamePlayer = StartCommand.game.getOtherAliveGamePlayer(seer.getKey());
            Player player = seer.getKey().getPlayer();
            Inventory inventory = seer.getValue().createInventorySeer(otherAliveGamePlayer);
            player.openInventory(inventory);
        }
    }

    public void checkIfPhaseTerminated() {
        boolean isPhaseTerminated = gameSeers.values().stream().allMatch(SeerGui::isChoiceValidated);
        if (isPhaseTerminated) {
            stopPhaseEngine();
        }
    }
}
