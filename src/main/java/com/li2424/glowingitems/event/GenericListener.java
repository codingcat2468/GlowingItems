package com.li2424.glowingitems.event;

import com.li2424.glowingitems.GlowingItems;
import org.bukkit.event.Listener;

public class GenericListener implements Listener {
    public GlowingItems plugin;

    public GenericListener(GlowingItems plugin) {
        this.plugin = plugin;
    }
}
