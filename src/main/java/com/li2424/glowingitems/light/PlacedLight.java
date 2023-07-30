package com.li2424.glowingitems.light;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlacedLight {
    private Location location;
    private Player source;
    private Material originalMaterial;

    public PlacedLight(Location location, Player source, Material originalMaterial) {
        this.location = location;
        this.source = source;
        this.originalMaterial = originalMaterial;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Player getSource() {
        return source;
    }

    public void setSource(Player source) {
        this.source = source;
    }

    public Material getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(Material originalMaterial) {
        this.originalMaterial = originalMaterial;
    }
}
