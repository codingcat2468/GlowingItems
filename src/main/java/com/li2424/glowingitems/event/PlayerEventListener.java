package com.li2424.glowingitems.event;

import com.li2424.glowingitems.GlowingItems;
import com.li2424.glowingitems.light.Light;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.InventoryHolder;

public class PlayerEventListener extends GenericListener {
    public PlayerEventListener(GlowingItems plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Light.updateForPlayer(plugin, event.getPlayer(), -1);
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Light.updateForPlayer(plugin, event.getPlayer(), -1);
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Light.updateForPlayer(plugin, event.getPlayer(), event.getNewSlot());
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Light.updateForPlayer(plugin, event.getPlayer(), -1);
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        InventoryHolder holder = event.getSource().getHolder();
        if (holder instanceof Player player) {
            Light.updateForPlayer(plugin, player, -1);
        }
    }

    @EventHandler
    public void onPlayerItemPickup(PlayerPickupItemEvent event) {
        Light.updateForPlayer(plugin, event.getPlayer(), -1);
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        Light.updateForPlayer(plugin, event.getPlayer(), -1);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Light.clear(plugin, event.getPlayer());
    }
}
