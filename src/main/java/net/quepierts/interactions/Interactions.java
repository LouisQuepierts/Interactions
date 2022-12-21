package net.quepierts.interactions;

import net.quepierts.interactions.main.CommandInteractions;
import net.quepierts.interactions.main.listener.EventListener;
import net.quepierts.interactions.main.Register;
import net.quepierts.interactions.main.config.Branch;
import net.quepierts.interactions.main.config.ConfigManager;
import net.quepierts.interactions.main.config.Entry;
import net.quepierts.interactions.main.data.Dependency;
import net.quepierts.interactions.main.data.action.ExecuteType;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import net.quepierts.interactions.main.listener.ItemsAdderEventListener;
import net.quepierts.interactions.main.runtime.TaskUpdatePlayerData;
import net.quepierts.interactions.main.utils.Time;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

public final class Interactions extends JavaPlugin {
    public static final File configPath = new File("plugins\\Interactions\\configs");
    public static Logger logger;
    public static Random random = new Random();

    private static Interactions instance;

    public static Interactions getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        logger = this.getLogger();
        logger.info("Initialization Start " + Time.getRuntimeMillis());

        Dependency.check();
        this.registerListeners();
        this.getServer().getScheduler().runTaskTimer(this, new TaskUpdatePlayerData(this), 0, 20);

        PluginCommand pluginCommand = Bukkit.getPluginCommand("interactions");
        if (pluginCommand != null) {
            CommandInteractions completer = new CommandInteractions();
            pluginCommand.setExecutor(completer);
            pluginCommand.setTabCompleter(completer);
        }

        load();
        logger.info("Initialization Finished");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void reload() {
        logger.info("Reloading Configs & Registries");
        instance.load();
        logger.info("Reloading Configs & Registries Finished");
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);

        if (Dependency.isItemsAdderLoaded()) {
            this.getServer().getPluginManager().registerEvents(new ItemsAdderEventListener(), this);
        }
    }

    private void load() {
        ExecuteType.cleanUp();
        TargetSlot.cleanUp();
        Branch.cleanUp();
        Entry.cleanUp();

        Register.loadStatic();

        Branch.close();
        Entry.close();
        ConfigManager.init(this);
    }
}
