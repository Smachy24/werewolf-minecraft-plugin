package werewolf.plugin.minecraft.roles;

import org.bukkit.Material;
import werewolf.plugin.minecraft.utils.ConfigItem;

public class Villager extends Role{

    public Villager() {
    }
    public Villager(String name, String team, String frenchName, String description, ConfigItem configItem) {
        super(name, team, frenchName, description, configItem);
    }


}
