package com.li2424.glowingitems.util;

import java.util.logging.Logger;

public class Messages {
    public static void showEnableMessage(Logger logger) {
        logger.info("GlowingItems is now enabled! Thank you for using my Plugin!");
        showInstructionsLink(logger);
    }

    public static void showInstructionsLink(Logger logger) {
        logger.info("You can find instructions here: " + "https://li2424.github.io/html/projects/project.html?type=minecraft&id=glowing-items");
    }

    public static void showDisableMessage(Logger logger) {
        logger.info("GlowingItems is stopping! Bye!");
    }

    public static void showDisabledMessage(Logger logger) {
        logger.info("GlowingItems is disabled because \"enabled\" property of the configuration is set to false or the configuration is invaild.");
        showInstructionsLink(logger);
    }
}
