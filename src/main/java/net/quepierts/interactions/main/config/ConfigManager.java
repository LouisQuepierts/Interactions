package net.quepierts.interactions.main.config;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.config.loader.ActionLoader;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    public static void reload() {
        if (!Interactions.configPath.exists()) {
            Interactions.configPath.mkdirs();
        }


        File[] configs =Interactions.configPath.listFiles(pathname -> pathname.toString().contains(".yml"));
        for (File config : configs) {
            loadItemDataConfig(config);
        }
    }

    private static void loadItemDataConfig(File file) {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        try {
            if (configuration.contains("actions")) {
                ActionLoader.loadActions(configuration.getConfigurationSection("actions"));
            }

            if (configuration.contains("static")) {

            }

            if (configuration.contains("private")) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (configuration.contains("actionSets")) {
//            actionSetCount += ActionSetLoader.loadActionSets(configuration.getConfigurationSection("actionSets"));
//        }
    }
}
