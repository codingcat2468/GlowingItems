package com.li2424.glowingitems;

import com.li2424.glowingitems.config.Config;
import com.li2424.glowingitems.event.PlayerEventListener;
import com.li2424.glowingitems.light.Light;
import com.li2424.glowingitems.light.PlacedLight;
import com.li2424.glowingitems.util.Messages;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public final class GlowingItems extends JavaPlugin {
    public ArrayList<PlacedLight> savedBlockStates;
    private BukkitTask updateDroppedItems;

    @Override
    public void onEnable() {
        savedBlockStates = new ArrayList<>();

        init();
        initTasks();
        if (Config.isEnabled(this)) {
            Messages.showEnableMessage(getLogger());
        } else {
            Messages.showDisabledMessage(getLogger());
        }
    }

    @Override
    public void onDisable() {
        Light.clearAll(this);
        Messages.showDisableMessage(getLogger());
    }

    public void init() {
        PluginManager manager = getServer().getPluginManager();
        Config.init(this);

        //register events
        manager.registerEvents(new PlayerEventListener(this), this);
    }

    public void initTasks() {
        updateDroppedItems = new BukkitRunnable() {
            @Override
            public void run() {
                Light.clearDeadEntities(GlowingItems.this);
                for (Player player : getServer().getOnlinePlayers())
                    Light.updateNearbyEntities(GlowingItems.this, player);
            }
        }.runTaskTimer(this, 5, 5);
    }
}
