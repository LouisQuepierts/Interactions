package net.quepierts.interactions.main;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.config.Entry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandInteractions implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            switch (args[0]) {
                case "reload":
                    Interactions.reload();
                    sender.sendMessage("§6Reload Configs");
                    return true;
                case "actions":
                    Entry.listActions(sender);
                    return true;
                case "conditions":
                    Entry.listConditions(sender);
                    return true;
            }
        } else if (args.length == 2) {
            switch (args[0]) {
                case "action":
                    return Entry.printAction(sender, args[1]);
                case "condition":
                    return Entry.printCondition(sender, args[1]);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "action", "actions", "condition", "conditions");
        } else if (args.length == 2) {
            switch (args[0]) {
                case "action":
                    return new ArrayList<>(Entry.actions());
                case "condition":
                    return new ArrayList<>(Entry.conditions());
            }
        }

        return Collections.emptyList();
    }
}
