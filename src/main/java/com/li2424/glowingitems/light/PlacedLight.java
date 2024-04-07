package com.li2424.glowingitems.light;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

public record PlacedLight(Location location, Entity source, LightSourceType sourceType, Material originalMaterial) { }
