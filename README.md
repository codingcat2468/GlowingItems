# GlowingItems
This is a Minecraft Spigot plugin for 1.20 that makes items like the torch glow when held in the hand.

I know other plugins like this already exist, but this one is compatible with the newest Spigot and Minecraft version 1.20 / 1.20.1!
## How to install it
This part is very simple. You just have to drag the GlowingItems-X.X.X.jar file from the Releases in the plugins folder of your server and you are done!

## Configuration
With the config.yml file, you can customize which items should glow when held in the hand. I also plan to add different brightness values for each item in the future.
This is the default configuration:
```yml
enabled: true
glowing_materials:
- TORCH
- SOUL_TORCH
- LANTERN
- SOUL_LANTERN
- GLOWSTONE
- GLOW_BERRIES
- GLOW_INK_SAC
- SEA_LANTERN
- LIGHT
- AMETHYST_SHARD
```
With the `enabled` property you can enable or disable the plugin.
The `glowing_materials` property is a list of every item that should glow. The entries must be in the format which bukkit uses in its `Material` class.
