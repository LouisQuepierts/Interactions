package net.quepierts.interactions.main.config;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.config.loader.ConditionLoader;
import net.quepierts.interactions.main.data.Registry;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class Branch {
    private static final Registry<Branch> registry = new Registry<>("Branch");

    public static void cleanUp() {
        registry.cleanUp();
    }

    public static void close() {
        registry.close();
    }

    public static Branch register(Entry entry, boolean required) {
        String name = entry.getName();
        return register(name, EnumBranchType.ENTRY, required);
    }

    public static Branch register(String id, EnumBranchType type, boolean required) {
        return register(id, type, required, null);
    }

    public static Branch register(String id, EnumBranchType type, boolean required, Object def) {
        return registry.register(id, new Branch(id, type, required, def));
    }

    public static Branch register(String id, String parentId, boolean required, Object def) {
        if (!registry.contains(parentId)) {
            Interactions.logger.warning("Unexisted parent: " + parentId);
            return null;
        }

        Interactions.logger.info(id);

        return registry.register(id, new Branch(id, registry.get(parentId), required, def));
    }

    @Nullable
    public static Branch getInstance(String id) {
        return registry.get(id);
    }

    @Nullable
    public static Branch getInstance(Entry entry) {
        return registry.get(entry.getName());
    }

    @Nullable
    public static Branch[] getInstance(String... ids) {
        List<Branch> branches = new ArrayList<>();

        for (String id : ids) {
            Branch branch = registry.get(id);

            if (branch != null) {
                branches.add(branch);
            }
        }

        return branches.toArray(new Branch[0]);
    }

    @Nullable
    public static List<Object> getBranchInfo(ConfigurationSection infoConfig, Branch[] branches) {
        List<Object> vars = new ArrayList<>();
        Set<String> keys = infoConfig.getKeys(false);

        boolean available = true;

        for (Branch branch : branches) {
            Object var = null;
            boolean flag = false;

            for (String key : keys) {
                if (key.equals(branch.name) || key.startsWith(branch.name + "_")) {
                    switch (branch.getType()) {
                        case STRING:
                            var = infoConfig.getString(key);
                            flag = true;
                            break;
//                        case STRING_ARRAY:
//                            var = infoConfig.getStringList(key).toArray(new String[0]);
//                            flag = true;
//                            break;
                        case BOOLEAN:
                            var = infoConfig.getBoolean(key);
                            flag = true;
                            break;
                        case NUMBER:
                            IMutableNumber<?> number = IMutableNumber.get(infoConfig.getString(key));
                            if (number == null) {
                                available = false;
                            }

                            var = number;
                            flag = true;
                            break;
                        case ENTRY: {
                            String name = branch.parent == null ? branch.name : branch.parent.name;
                            Entry entry = Entry.getInstance(name);

                            ConfigurationSection section = infoConfig.getConfigurationSection(key);
                            if (section != null) {
                                Object object = entry.getObject(section);
                                if (object == null) {
                                    available = false;
                                }

                                var = object;
                                flag = true;
                            }
                            break;
                        }
                        case CONDITIONS: {
                            List<AbstractCondition> conditions = ConditionLoader.getConditions(infoConfig.getConfigurationSection(key));
                            if (conditions.isEmpty()) {
                                available = false;
                            }

                            var = conditions;
                            flag = true;
                            break;
                        }
                    }

                    if (branch.legalChecker == null || branch.legalChecker.legal(var)) {
                        vars.add(var);
                    } else {
                        Interactions.logger.warning("Illegal or missing value for branch: " + branch.name + ", value: " + var);
                        available = false;
                    }
                }
            }

            if (!flag) {
                if (branch.isRequired()) {
                    Interactions.logger.warning("Entry [" + infoConfig.getName() + "] missing or invaluable key: " + branch.getName());
                    return null;
                } else {
                    switch (branch.getType()) {
                        case NUMBER:
                            String def;
                            if (branch.def instanceof Number) {
                                def = IMutableNumber.parseString((Number) branch.def);
                            } else {
                                def = (String) branch.def;
                            }
                            var = IMutableNumber.get(infoConfig.getString(branch.name, def));
                            break;
                        case STRING:
                            var = infoConfig.getString(branch.name, (String) branch.def);
                            break;
                        case BOOLEAN:
                            var = infoConfig.getBoolean(branch.name, (Boolean) branch.def);
                            break;
//                        case SYSTEM:
//                            var = infoConfig.get(branch.name);
//                            break;
//                        case ENTRY: {
//                            Entry<?> entry = Entry.getInstance(branch.name);
//                            Object object = entry.getObject(infoConfig.getConfigurationSection(branch.name));
//                            if (object == null) {
//                                return null;
//                            }
//
//                            var = object;
//                            break;
//                        }
                    }

                    vars.add(var);
                }
            }
        }


        return available ? vars : null;
    }

    private final Branch parent;
    private final String name;
    private final EnumBranchType type;
    private final boolean required;
    private final Object def;
    private ILegalChecker legalChecker = null;

    public Branch(String name, EnumBranchType type, boolean required, Object def) {
        this.parent = null;
        this.name = name;
        this.type = type;
        this.required = required;
        this.def = def;
    }

    public Branch(String name, Branch parent, boolean required, Object def) {
        this.parent = parent;
        this.name = name;
        this.required = required;
        this.def = def;
        this.type = parent.type;
    }

    public String getName() {
        return name;
    }

    public void print(CommandSender sender, final int deep) {
        StringBuilder builder = new StringBuilder("§6");
        for (int i = deep; i > 0; i--) {
            builder.append("  ");
        }

        if (type.equals(EnumBranchType.ENTRY)) {
            Entry.getInstance(this.name).print(sender, deep);
        } else {
            sender.sendMessage(builder + this.name + ": §b" + this.type.name());
        }
    }

    public EnumBranchType getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public <T> void bind(ILegalChecker<T> legalChecker) {
        if (this.legalChecker == null) {
            this.legalChecker = legalChecker;
        }
    }

    public enum EnumBranchType {
        STRING, BOOLEAN, NUMBER, SYSTEM, ENTRY, CONDITIONS;

        @Nullable
        public static EnumBranchType fromString(@NotNull String name) {
            for (EnumBranchType value : EnumBranchType.values()) {
                if (value.name().equalsIgnoreCase(name)) {
                    return value;
                }
            }

            return null;
        }
    }
}
