package net.quepierts.interactions;

import net.quepierts.interactions.main.CommandInteractions;
import net.quepierts.interactions.main.EventListener;
import net.quepierts.interactions.main.Register;
import net.quepierts.interactions.main.config.Branch;
import net.quepierts.interactions.main.config.ConfigManager;
import net.quepierts.interactions.main.config.Entry;
import net.quepierts.interactions.main.data.Dependency;
import net.quepierts.interactions.main.data.action.ActionManager;
import net.quepierts.interactions.main.data.action.ExecuteType;
import net.quepierts.interactions.main.data.invnetory.InventoryData;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import net.quepierts.interactions.main.runtime.TaskUpdatePlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

public final class Interactions extends JavaPlugin {
    public static final File configPath = new File("plugins\\Interactions\\actions");
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
        logger.info("Initialization Start");

        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryData(), this);
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

    private void load() {
        ExecuteType.cleanUp();
        TargetSlot.cleanUp();
        Branch.cleanUp();
        Entry.cleanUp();

        Dependency.check();

        Register.loadStatic();
        ConfigManager.init(this);
    }
}
