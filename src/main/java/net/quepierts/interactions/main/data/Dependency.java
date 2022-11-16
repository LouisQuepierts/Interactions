package net.quepierts.interactions.main.data;

import org.bukkit.Bukkit;

public class Dependency {
    private static boolean iaLoaded;

    public static void check() {
        iaLoaded = Bukkit.getPluginManager().isPluginEnabled("ItemsAdder");
    }

    public static boolean isItemsAdderLoaded() {
        return iaLoaded;
    }
}
