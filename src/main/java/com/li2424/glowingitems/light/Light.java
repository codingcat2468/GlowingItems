package com.li2424.glowingitems.light;

import com.li2424.glowingitems.GlowingItems;
import com.li2424.glowingitems.config.Config;
import com.li2424.glowingitems.config.GlowingMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Light {
    private static final Material LIGHT_MATERIAL = Material.LIGHT;

    //Change inventory slots for lights here
    public static List<Material> checks(Player player) {
        PlayerInventory inv = player.getInventory();
        return List.of(
                inv.getItemInMainHand().getType(),
                inv.getItemInOffHand().getType()
        );
    }

    public static void updateForPlayer(GlowingItems plugin, Player player, int specificSlot) {
        Light.clear(plugin, player);

        List<Material> specSlotLst = new ArrayList<>();
        if (specificSlot != -1) {
            ItemStack specSlotStack = player.getInventory().getItem(specificSlot);
            if (specSlotStack != null) {
                specSlotLst.add(specSlotStack.getType());
            }
            for (Material mat :
                    Light.checks(player)) {
                if ((specSlotStack != null) && (mat == player.getInventory().getItemInMainHand().getType())) continue;
                specSlotLst.add(mat);
            }
        }

        executeAtSafeBlock(player.getLocation(), height -> {
            int highestLevel = 0;
            for (Material material :
                    (specificSlot == -1) ? Light.checks(player) : specSlotLst
            ) {
                int level = Light.getLevel(material, plugin);
                if (level == -1) continue;
                if (highestLevel >= level) continue;

                highestLevel = level;
            }

            if (highestLevel > 0) {
                Light.addSourceAt(plugin, player, LightSourceType.PLAYER, height, Math.min(highestLevel + height, 15));
            }
        });

        if (Config.checkDroppedItems(plugin))
            Light.updateNearbyEntities(plugin, player);
    }

    public static void updateForItemEntity(GlowingItems plugin, Item itemEntity) {
        Light.clear(plugin, itemEntity);

        executeAtSafeBlock(itemEntity.getLocation(), height -> {
            Material item = itemEntity.getItemStack().getType();
            int level = Light.getLevel(item, plugin);
            if (level <= 0) return;

            Light.addSourceAt(plugin, itemEntity, LightSourceType.ITEM, height, level);
        });
    }

    public static void updateNearbyEntities(GlowingItems plugin, Player player) {
        int scanBoxSize = Config.getEntityScanBoxSize(plugin);
        List<Entity> nearbyEntities = player.getNearbyEntities(scanBoxSize, scanBoxSize, scanBoxSize);
        nearbyEntities.stream()
                .filter(entity -> entity.getType() == EntityType.DROPPED_ITEM)
                .forEach(entity -> Light.updateForItemEntity(plugin, (Item) entity));
    }

    private static void executeAtSafeBlock(Location location, Consumer<Integer> action) {
        for (int h = 0; h < 3; h++) {
            if (checkForAir(location, h)) {
                action.accept(h);
                break;
            }
        }
    }

    public static void clear(GlowingItems plugin, Entity entity) {
        for (Iterator<PlacedLight> it = plugin.savedBlockStates.iterator(); it.hasNext(); ) {
            PlacedLight light = it.next();
            World world = light.location().getWorld();

            if (world != null) {
                if (light.source().getUniqueId() == entity.getUniqueId()) {
                    sendClientBlock(plugin, light.location(), light.originalMaterial().createBlockData());
                    it.remove();
                }
            }
        }
    }

    public static void clearDeadEntities(GlowingItems plugin) {
        for (Iterator<PlacedLight> it = plugin.savedBlockStates.iterator(); it.hasNext(); ) {
            PlacedLight light = it.next();
            if (plugin.getServer().getEntity(light.source().getUniqueId()) != null) return;

            World world = light.location().getWorld();
            if (world != null) {
                sendClientBlock(plugin, light.location(), light.originalMaterial().createBlockData());
                it.remove();
            }
        }
    }

    public static void clearAll(GlowingItems plugin) {
        for (Iterator<PlacedLight> it = plugin.savedBlockStates.iterator(); it.hasNext(); ) {
            PlacedLight light = it.next();
            World world = light.location().getWorld();

            if (world != null) {
                sendClientBlock(plugin, light.location(), light.originalMaterial().createBlockData());
                it.remove();
            }
        }
    }

    public static boolean checkForAir(Location location, int yOffset) {
        World world = location.getWorld();
        if (world == null)
            throw new IllegalArgumentException("Location has invalid world");

        Block b = world.getBlockAt(location.add(0, yOffset, 0));
        return (b.getType() == Material.AIR) || (b.getType() == Material.CAVE_AIR);
    }

    public static int getLevel(Material material, GlowingItems plugin) {
        List<GlowingMaterial> materials = Config.getGlowMaterials(plugin);

        for (GlowingMaterial mat :
                materials) {
            if (mat.material() == material) {
                return mat.level();
            }
        }

        return -1;
    }

    public static void addSourceAt(GlowingItems plugin, Entity entity, LightSourceType sourceType, int yOffset, int level) {
        World world = entity.getWorld();
        Location location = entity.getLocation().add(0, yOffset, 0);
        if (plugin.savedBlockStates.stream().anyMatch(source -> source.location().equals(location))) return;

        Block block = world.getBlockAt(location);
        plugin.savedBlockStates.add(new PlacedLight(location, entity, sourceType, block.getType()));
        sendClientLightBlock(plugin, location, level);
    }

    private static void sendClientLightBlock(GlowingItems plugin, Location location, int level) {
        Levelled data = (Levelled) LIGHT_MATERIAL.createBlockData();
        data.setLevel(level);
        sendClientBlock(plugin, location, data);
    }

    private static void sendClientBlock(GlowingItems plugin, Location location, BlockData blockData) {
        Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
        for (Player player : players) {
            player.sendBlockChange(location, blockData);
        }
    }
}
