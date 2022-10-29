package net.quepierts.interactions;

import net.quepierts.interactions.main.EventListener;
import net.quepierts.interactions.main.Register;
import net.quepierts.interactions.main.config.ConfigManager;
import net.quepierts.interactions.main.data.action.ActionManager;
import net.quepierts.interactions.main.data.invnetory.InventoryData;
import net.quepierts.interactions.main.runtime.TaskUpdatePlayerData;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class Interactions extends JavaPlugin {
    public static final File configPath = new File("plugins\\Interactions\\actions");
    public static Logger logger;

    @Override
    public void onEnable() {
        logger = this.getLogger();
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryData(), this);
        this.getServer().getScheduler().runTaskTimer(this, new TaskUpdatePlayerData(this), 0, 20);
        logger.info("Interaction Initialization");

        Register.loadStatic();
        ActionManager.init();
        InventoryData.init(this);

        ConfigManager.reload();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
