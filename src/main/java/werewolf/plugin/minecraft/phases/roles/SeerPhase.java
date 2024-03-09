package werewolf.plugin.minecraft.phases.roles;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import werewolf.plugin.minecraft.GamePlayer;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.commands.StartCommand;
import werewolf.plugin.minecraft.menus.SeerGui;
import werewolf.plugin.minecraft.utils.Title;

import java.util.List;
import java.util.Properties;

public class SeerPhase extends Phase{
    private int duration;
    private Inventory inventory;
    private List<GamePlayer> players;
    private BukkitRunnable phaseRunnable;

    public SeerPhase(){
        this.setProperties();
        this.phaseEngine();
    }

    @Override
    public String getPhaseName() {
        return "seer";
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
        this.players = StartCommand.getCurrentGame().getGamePlayersByRoleName("Seer");
        Title.sendTitleToEveryone(ChatColor.GREEN + players.get(0).getRole().getFrenchName(),
                ChatColor.BLUE + "C'est à vous !");
        new BukkitRunnable() {
            @Override
            public void run() {
                for(GamePlayer gamePlayer: players) {
                    List<GamePlayer> otherAliveGamePlayer = StartCommand.game.getOtherAliveGamePlayer(gamePlayer);
                    inventory = SeerGui.createInventorySeer(otherAliveGamePlayer);
                    gamePlayer.getPlayer().openInventory(inventory);
                }
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

    @Override
    public void endPhase() {
        for(GamePlayer gamePlayer: this.players) {
            gamePlayer.getPlayer().closeInventory();
        }
        Title.sendTitleToEveryone(ChatColor.GREEN + players.get(0).getRole().getFrenchName(),
                ChatColor.BLUE + "Au dodo !");
    }

}
