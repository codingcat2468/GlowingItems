# GlowingItems
GlowingItems is a Minecraft Spigot plugin for 1.20 that makes items like the torch glow when held in the hand.

I know other plugins like this already exist, but this one is compatible with the newest Spigot and Minecraft version 1.20 / 1.20.1! (It should also work on 1.20.2 - 1.20.4, if you encounter any bugs, feel free to open an issue!)
## How to install it
This part is very simple. You just have to drag the GlowingItems-X.X.X.jar file from the Releases in the plugins folder of your server and you are done!

## Configuration
In the config.yml file, you can customize which items should glow when held in the hand. You can also customize the light level for each material.
This is the default configuration:
```yml
enabled: true
glowing_materials:
  TORCH:
    level: 15
  SOUL_TORCH:
    level: 10
  LANTERN:
    level: 14
  SOUL_LANTERN:
    level: 10
  GLOWSTONE:
    level: 15
  GLOW_BERRIES:
    level: 12
  GLOW_INK_SAC:
    level: 12
  SEA_LANTERN:
    level: 15
  AMETHYST_SHARD:
    level: 6
  AMETHYST_CLUSTER:
    level: 8
```
The `enabled` enables or disables the plugin.
Using the `glowing_materials` property, you can customize which materials should glow. You can also set the light levels here (Minecraft light levels go from 1 to 15).
You can find a list of all materials at the [SpigotMC Javadocs](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html).