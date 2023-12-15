package com.li2424.glowingitems.config;

import org.bukkit.Material;

public class GlowingMaterial {
    private final Material material;
    private final int level;

    public GlowingMaterial(Material material, int level) {
        this.material = material;
        this.level = level;
    }

    public Material getMaterial() {
        return material;
    }

    public int getLevel() {
        return level;
    }
}
