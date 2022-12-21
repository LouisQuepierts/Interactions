package net.quepierts.interactions.main.config.loader;

import net.quepierts.interactions.main.data.var.VarManager;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Set;

public class AnnouncementLoader {
    public static int loadAnnouncements(final ConfigurationSection config) {
        int count = 0;

        Set<String> keys = config.getKeys(false);

        for (String key : keys) {
            switch (key) {
                case "vars":
                    VarManager.load(config.getConfigurationSection(key));
                    break;
                case "armorSets":

                    break;
            }
        }

        return count;
    }

    /*private static int getAnnouncements(final ConfigurationSection config, boolean global) {
        int count = 0;
        Set<String> keys = config.getKeys(false);

        for (String key : keys) {
            switch (key) {
                case "vars":
                    VarManager.load(config.getConfigurationSection(key), global);
                    break;
                case "armorSets":

                    break;
            }
        }
        return count;
    }*/
}
