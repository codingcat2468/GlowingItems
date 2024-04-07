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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

        List<Material> specSlotLst = null;
        if (specificSlot != -1) {
            ItemStack specSlotStack = player.getInventory().getItem(specificSlot);
            specSlotLst = new ArrayList<>();
            if (specSlotStack != null) {
                specSlotLst.add(specSlotStack.getType());
            }
            for (Material mat :
                    Light.checks(player)) {
                if ((specSlotStack != null) && (mat == player.getInventory().getItemInMainHand().getType())) continue;
                specSlotLst.add(mat);
            }
        }

        for (int i = 0; i < 3; i++) {
            if (checkForAir(player, i)) {
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
                    Light.addSourceAt(plugin, player, i, highestLevel);
                }

                break;
            }
        }
    }

    public static void clear(GlowingItems plugin, Player player) {
        for (Iterator<PlacedLight> it = plugin.savedBlockStates.iterator(); it.hasNext(); ) {
            PlacedLight light = it.next();
            World world = light.getLocation().getWorld();

            if (world != null) {
                if (light.getSource() == player) {
                    if (world.getBlockAt(light.getLocation()).getType() != Material.LIGHT) continue;

                    world.getBlockAt(light.getLocation())
                            .setType(light.getOriginalMaterial());
                    sendClientBlock(plugin, light.location(), light.originalMaterial().createBlockData());
                    it.remove();
                }
            }
        }
    }

    public static void clearAll(GlowingItems plugin) {
        for (Iterator<PlacedLight> it = plugin.savedBlockStates.iterator(); it.hasNext(); ) {
            PlacedLight light = it.next();
            World world = light.getLocation().getWorld();

            if (world != null) {
                sendClientBlock(plugin, light.location(), light.originalMaterial().createBlockData());
                it.remove();
            }
        }
    }

    public static boolean checkForAir(Player p, int yOffset) {
        World world = p.getWorld();
        Block b = world.getBlockAt(p.getLocation().add(0, yOffset, 0));

        return (b.getType() == Material.AIR) || (b.getType() == Material.CAVE_AIR);
    }

    public static int getLevel(Material material, GlowingItems plugin) {
        List<GlowingMaterial> materials = Config.getGlowMaterials(plugin);

        for (GlowingMaterial mat :
                materials) {
            if (mat.getMaterial() == material) {
                return mat.getLevel();
            }
        }

        return -1;
    }

    public static void addSourceAt(GlowingItems plugin, Player player, int yOffset, int level) {
        World world = player.getWorld();
        Location location = player.getLocation().add(0, yOffset, 0);
        Block block = world.getBlockAt(location);

        plugin.savedBlockStates.add(new PlacedLight(location, player, block.getType()));
        block.setType(Material.LIGHT);
        changeLightLevel(block, level);
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
