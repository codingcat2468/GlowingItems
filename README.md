# GlowingItems
GlowingItems is a modern Minecraft Spigot plugin for 1.20 - 1.20.4 that makes items like the torch light up when held in the hand or dropped on the floor.

## How to install it
This part is very simple. You just have to drag the GlowingItems-X.X.X.jar file from the Releases in the plugins folder of your server and you are done!

## Configuration
In the config.yml file, you can customize which items should glow when held in the hand. You can also customize the light level for each material.

The `enabled` enables or disables the plugin.
Using the `glowing_materials` property, you can customize which materials should glow. You can also set the light levels here (Minecraft light levels go from 1 to 15).
You can toggle dropped items glowing using `check-dropped-items`, the range around each player in that dropped items will be updated can be changed using `entity-scan-box-size`. Make sure to not turn this option up too much to avoid performance issues!
You can find a list of all materials at the [SpigotMC Javadocs](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html).

Find out more at [my blog](https://codingcat2468.github.io/minecraft/2024/04/07/glowing-items-plugin.html)!
