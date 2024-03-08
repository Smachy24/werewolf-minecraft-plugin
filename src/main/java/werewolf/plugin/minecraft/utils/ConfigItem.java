package werewolf.plugin.minecraft.utils;

import org.bukkit.Material;

public enum ConfigItem {
    BROWN_CONCRETE(Material.BROWN_CONCRETE),
    EMERALD_BLOCK(Material.EMERALD_BLOCK),
    ENDER_EYE(Material.ENDER_EYE);

    private final Material material;

    ConfigItem(Material material) {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }

        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
