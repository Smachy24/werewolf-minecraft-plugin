package werewolf.plugin.minecraft.utils;

import org.bukkit.Material;

public enum ConfigItem {
    BROWN_CONCRETE(Material.BROWN_CONCRETE),
    EMERALD(Material.EMERALD),
    ENDER_EYE(Material.ENDER_EYE);

    private final Material material;

    ConfigItem(Material material) {
        // Vérifiez si le matériau est nul, et si c'est le cas, lancez une exception
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }

        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
