package com.li2424.glowingitems.config;

import com.li2424.glowingitems.GlowingItems;
import com.li2424.glowingitems.util.Messages;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Config {

    private static final List<GlowingMaterial> DEFAULTS = List.of(
            new GlowingMaterial(Material.TORCH, 14), new GlowingMaterial(Material.SOUL_TORCH, 10), new GlowingMaterial(Material.LANTERN, 14),
            new GlowingMaterial(Material.TORCH, 15), new GlowingMaterial(Material.SOUL_LANTERN, 10), new GlowingMaterial(Material.GLOWSTONE, 15),
            new GlowingMaterial(Material.GLOW_BERRIES, 12), new GlowingMaterial(Material.GLOW_INK_SAC, 12), new GlowingMaterial(Material.SEA_LANTERN, 15),
            new GlowingMaterial(Material.AMETHYST_SHARD, 6), new GlowingMaterial(Material.AMETHYST_CLUSTER, 8)
    );

    @Nonnull
    public static List<GlowingMaterial> getGlowMaterials(GlowingItems plugin) {
        if (!isEnabled(plugin))
            return new ArrayList<>();
        try {
            FileConfiguration config = plugin.getConfig();
            List<GlowingMaterial> materials = new ArrayList<>();
            Set<String> keys = Objects.requireNonNull(config.getConfigurationSection("glowing_materials")).getKeys(false);
            for (String key :
                    keys) {
                Material material = Material.valueOf(key);
                int level = Integer.parseInt(Objects.requireNonNull(config.getString("glowing_materials." + key + ".level")));

                materials.add(new GlowingMaterial(material, level));
            }

            return materials;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static boolean isEnabled(GlowingItems plugin) {
        try {
            return plugin.getConfig().getBoolean("enabled");
        } catch (Exception e) {
            return false;
        }
    }

    public static void init(GlowingItems plugin) {
        FileConfiguration config = plugin.getConfig();

        //config migration
        List<String> glowingMaterialsOld = config.getStringList("glowing_materials");
        if (!glowingMaterialsOld.isEmpty()) {
            Messages.showConfigMigrating(plugin.getLogger());
            config.createSection("glowing_materials");

            for (String oldMaterial :
                    glowingMaterialsOld) {
                String path = "glowing_materials." + oldMaterial;
                config.set(path, new Object());
                config.set(path + ".level", 15);
            }
            plugin.saveConfig();
            plugin.reloadConfig();

            Messages.showConfigMigrated(plugin.getLogger());
        }

        //default config
        config.addDefault("enabled", true);

        config.addDefault("glowing_materials", new Object());
        for (GlowingMaterial material :
                Config.DEFAULTS) {
            String path = "glowing_materials." + material.getMaterial().toString();
            config.addDefault(path, new Object());
            config.addDefault(path + ".level", material.getLevel());
        }
        config.options().copyDefaults(true);

        plugin.saveConfig();
        plugin.reloadConfig();
    }
}
