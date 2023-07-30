package com.li2424.glowingitems.config;

import com.li2424.glowingitems.GlowingItems;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {
    @Nonnull
    public static List<Material> getGlowMaterials(GlowingItems plugin) {
        if (!isEnabled(plugin))
            return new ArrayList<>();
        try {
            List<String> materials = plugin.getConfig().getStringList("glowing_materials");
            return materials.stream().map(Material::valueOf).toList();
        } catch (Exception e) {
            plugin.getLogger().warning("Configuration invalid! Cannot load data!");
            return new ArrayList<>();
        }
    }

    public static boolean isEnabled(GlowingItems plugin) {
        try {
            return plugin.getConfig().getBoolean("enabled");
        } catch (Exception e) {
            plugin.getLogger().warning("Configuration invalid! Cannot load data!");
            return false;
        }
    }

    public static void init(GlowingItems plugin) {
        FileConfiguration config = plugin.getConfig();

        //default config
        config.addDefault("enabled", true);
        config.addDefault("glowing_materials", Arrays.asList("TORCH", "SOUL_TORCH", "LANTERN", "SOUL_LANTERN", "GLOWSTONE", "GLOW_BERRIES", "GLOW_INK_SAC", "LIGHT"));
        config.options().copyDefaults(true);

        plugin.saveConfig();
        plugin.reloadConfig();
    }
}
