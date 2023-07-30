package com.li2424.glowingitems.event;

import com.li2424.glowingitems.GlowingItems;
import com.li2424.glowingitems.light.Light;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.*;

public class PlayerEventListener extends GenericListener {
    public PlayerEventListener(GlowingItems plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Light.updateForPlayer(plugin, event.getPlayer());
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Light.updateForPlayer(plugin, event.getPlayer());
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Light.updateForPlayer(plugin, event.getPlayer());
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        Light.updateForPlayer(plugin, event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        Light.clear(plugin, p);
    }
}
