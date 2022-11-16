package net.quepierts.interactions.main.config;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.config.loader.ActionLoader;
import net.quepierts.interactions.main.data.action.ActionManager;
import net.quepierts.interactions.main.data.invnetory.InventoryData;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {
    private static int actionCount;
    public static void init(Interactions interactions) {
        actionCount = 0;

        Interactions.logger.info("Config Loading");
        if (!Interactions.configPath.exists()) {
            Interactions.configPath.mkdirs();
        }

        ActionManager.init();

        File[] configs = Interactions.configPath.listFiles(pathname -> pathname.toString().contains(".yml"));
        for (File config : configs) {
            loadItemDataConfig(config);
        }

        Interactions.logger.info("Action loaded " + actionCount + " entries");

        InventoryData.init(interactions);
        Interactions.logger.info("Config Load Finished");
    }

    private static void loadItemDataConfig(File file) {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        if (configuration.contains("actions")) {
            actionCount += ActionLoader.loadActions(configuration.getConfigurationSection("actions"));
        }

        if (configuration.contains("static")) {

        }

        if (configuration.contains("private")) {

        }

//        if (configuration.contains("actionSets")) {
//            actionSetCount += ActionSetLoader.loadActionSets(configuration.getConfigurationSection("actionSets"));
//        }
    }
}
