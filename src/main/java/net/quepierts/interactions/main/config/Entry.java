package net.quepierts.interactions.main.config;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.api.AbstractCondition;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class Entry {
    private static final Map<String, Entry> entryMap = new TreeMap<>();
    private static final Map<String, Entry> actions = new TreeMap<>();
    private static final Map<String, Entry> conditions = new TreeMap<>();

    public static boolean hasEntry(String id) {
        return entryMap.containsKey(id);
    }

    public static Set<String> actions() {
        return actions.keySet();
    }

    public static Set<String> conditions() {
        return conditions.keySet();
    }

    public static boolean printAction(CommandSender sender, String actionName) {
        sender.sendMessage("§8[§eInteractions Config§8]");
        Entry entry = actions.get(actionName);
        if (entry != null) {
            entry.print(sender, 0);
            return true;
        }

        return false;
    }

    public static boolean printCondition(CommandSender sender, String conditionName) {
        sender.sendMessage("§8[§eInteractions Config§8]");
        Entry entry = conditions.get(conditionName);
        if (entry != null) {
            entry.print(sender, 0);
            return true;
        }

        return false;
    }

    public static void listActions(CommandSender sender) {
        sender.sendMessage("§8[§eInteractions§8]");
        sender.sendMessage("§6Registered Actions");
        for (String action : actions.keySet()) {
            sender.sendMessage("§b- " + action);
        }
    }

    public static void listConditions(CommandSender sender) {
        sender.sendMessage("§8[§eInteractions§8]");
        sender.sendMessage("§6Registered Conditions: ");
        for (String condition : conditions.keySet()) {
            sender.sendMessage("§b- " + condition);
        }
    }

    public static void cleanUp() {
        entryMap.clear();
        actions.clear();
        conditions.clear();
    }

    @Nullable
    public static Entry getInstance(String id) {
        return entryMap.get(id);
    }

    public static Registrar getRegistrar(String name) {
        return new Registrar(name, 0);
    }

    public static Registrar getRegistrar(Class<?> clazz) {
        return getRegistrar(clazz, clazz.getSimpleName());
    }

    public static Registrar getRegistrar(Class<?> clazz, String name) {
        if (AbstractAction.class.isAssignableFrom(clazz)) {
            Registrar action = new Registrar("action_" + name.replace("Action", ""), 1);
            return action;
        } else if (AbstractCondition.class.isAssignableFrom(clazz)) {
            return new Registrar("condition_" + name.replace("Condition", ""), 2);
        } else {
            return new Registrar(name, 0);
        }
    }

    public static void register(String id, Entry entry) {
        if (!entryMap.containsKey(id)) {
            entryMap.put(id, entry);
        }
    }

    private final String name;
    private IConstructor constructor;

    private Branch[] branches;
    private String executeType = null;

    private boolean multiRoots = false;

    private Entry(String name) {
        this.name = name;
    }

    private Entry(String name, Entry entry) {
        this(name);

        constructor = entry.constructor;
        executeType = entry.executeType;
        multiRoots = entry.multiRoots;
    }

    public static void close() {
    }

    public String getName() {
        return name;
    }

    public boolean isMultiRoots() {
        return multiRoots;
    }

    public void print(CommandSender sender, final int deep) {
        StringBuilder builder = new StringBuilder("§6");
        for (int i = deep; i > 0; i--) {
            builder.append("  ");
        }

        sender.sendMessage(builder + this.name + ":");
        for (Branch branch : branches) {
            branch.print(sender, deep + 1);
        }
    }

    @Nullable
    public Object getObject(ConfigurationSection config) {
        List<Object> branchInfo = Branch.getBranchInfo(config, branches);
        if (branchInfo == null) {
            Interactions.logger.warning("Illegal or missing values for entry: " + config.getName());
            return null;
        }

        try {
            return constructor.construct(branchInfo.toArray(new Object[0]));
        } catch (Exception ignored) {
            return null;
        }
    }

    @Nullable
    public Object getObject(ConfigurationSection config, Object... extraArgs) {
        List<Object> branchInfo = Branch.getBranchInfo(config, branches);
        if (branchInfo == null) {
            Interactions.logger.warning("Illegal or missing values for entry: " + config.getName());
            return null;
        }

        Collections.addAll(branchInfo, extraArgs);
        try {
            return constructor.construct(branchInfo.toArray(new Object[0]));
        } catch (Exception ignored) {
            Interactions.logger.warning("Errors due to Illegal or Missing value during constructing entry: " + config.getName());
            return null;
        }
    }

    @Nullable
    public Object getObject(Object... args) {
        try {
            return constructor.construct(args);
        } catch (Exception ignored) {
            return null;
        }
    }

    public String getExecuteType() {
        return executeType;
    }

    public final static class Registrar {
        private final Entry entry;
        private final List<Branch> branches = new ArrayList<>();
        private final int flag;

        private boolean built = false;
        private boolean asBranch = false;

        private Registrar(String name, int flag) {
            entry = new Entry(name);
            this.flag = flag;
        }

        public Registrar addBranch(Branch branch) {
            if (!branches.contains(branch)) {
                branches.add(branch);
            }
            return this;
        }

        public Registrar addBranch(Branch... branch) {
            for (Branch b : branch) {
                addBranch(b);
            }
            return this;
        }

        public Registrar addBranch(String id) {
            addBranch(Branch.getInstance(id));
            return this;
        }

        public Registrar subEntry(Entry entry) {
            Branch.getInstance(entry);
            return this;
        }

        public Registrar asRoot(String type) {
            entry.executeType = type;
            return this;
        }

        public Registrar multiRoots() {
            entry.multiRoots = true;
            return this;
        }

        public Registrar asBranch() {
            asBranch = true;
            return this;
        }

        public Registrar bind(IConstructor iConstructor) {
            entry.constructor = iConstructor;
            return this;
        }

        public void register() {
            if (!built) {
                built = true;

                add(this, entry, flag);
                entry.branches = branches.toArray(new Branch[0]);

                if (asBranch) {
                    Branch.register(entry, true);
                }
            }
        }

        public Registrar registerSub(String subName, boolean prefix) {
            String name = this.entry.name;
            if (prefix) {
                String[] split = name.split("_");
                name = split[0] + "_" + subName + split[1];
            } else {
                name += subName;
            }

            Entry entry = new Entry(name, this.entry);

            add(this, entry, flag);
            entry.branches = branches.toArray(new Branch[0]);

            if (asBranch) {
                Branch.register(entry, true);
            }

            return this;
        }

        private static void add(Registrar registrar, Entry entry, int flag) {
            entryMap.put(entry.name, entry);

            switch (flag) {
                case 1:
                    registrar.addBranch("conditions");
                    actions.put(entry.name.replace("action_", ""), entry);
                    return;
                case 2:
                    conditions.put(entry.name.replace("condition_", ""), entry);
            }
        }
    }
}
