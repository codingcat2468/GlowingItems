package com.li2424.glowingitems.util;

import java.util.logging.Logger;

public class Messages {
    public static void showEnableMessage(Logger logger) {
        logger.info("GlowingItems is now enabled, thank you for using my Plugin!");
        showInstructionsLink(logger);
    }

    public static void showInstructionsLink(Logger logger) {
        logger.info("You can find instructions here: " + "https://codingcat2468.github.io/minecraft/2024/04/07/glowing-items-plugin.html");
    }

    public static void showConfigMigrating(Logger logger) {
        logger.warning("You are using the old configuration from version 1.0.1 or below! Your configuration will be migrated to the new version to support individual light levels for materials.");
    }

    public static void showConfigMigrated(Logger logger) {
        logger.info("The configuration has been successfully migrated! If you want the individual light levels to work correctly, you have to change them manually in the config!");
    }

    public static void showDisableMessage(Logger logger) {
        logger.info("GlowingItems is stopping! Bye!");
    }

    public static void showDisabledMessage(Logger logger) {
        logger.info("GlowingItems is disabled because \"enabled\" property of the configuration is set to false or the configuration is invalid.");
        showInstructionsLink(logger);
    }
}
