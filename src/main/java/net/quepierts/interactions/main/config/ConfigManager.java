package net.quepierts.interactions.main.config;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.config.loader.ActionLoader;
import net.quepierts.interactions.main.config.loader.AnnouncementLoader;
import net.quepierts.interactions.main.data.AvailableIDs;
import net.quepierts.interactions.main.data.action.ActionManager;
import net.quepierts.interactions.main.data.invnetory.InventoryData;
import net.quepierts.interactions.main.data.var.VarManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {
    public static final File deprecatedPath = new File("plugins\\Interactions\\actions");

    private static int actionCount;
    public static void init(Interactions interactions) {
        actionCount = 0;

        Interactions.logger.info("Config Loading");
        if (deprecatedPath.exists()) {
            File file = Interactions.configPath.getAbsoluteFile();
            deprecatedPath.renameTo(file);
        } else if (!Interactions.configPath.exists()) {
            Interactions.configPath.mkdirs();
        }

        ActionManager.init();
        VarManager.cleanUp();
        AvailableIDs.cleanUp();

        File[] configs = Interactions.configPath.listFiles(pathname -> pathname.toString().contains(".yml"));
        for (File config : configs) {
            loadItemDataConfig(config);
        }

        Interactions.logger.info("Action loaded " + actionCount + " entries");

        InventoryData.init(interactions);
        VarManager.init(interactions);
        Interactions.logger.info("Config Load Finished");
    }

    private static void loadItemDataConfig(File file) {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection config;

        config = configuration.getConfigurationSection("announcements");
        if (config != null) {
            AnnouncementLoader.loadAnnouncements(config);
        }

        config = configuration.getConfigurationSection("actions");
        if (config != null) {
            actionCount += ActionLoader.loadActions(config);
        }

//        if (configuration.contains("actionSets")) {
//            actionSetCount += ActionSetLoader.loadActionSets(configuration.getConfigurationSection("actionSets"));
//        }
    }
}
