package net.quepierts.interactions.main.config;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.config.loader.ConditionLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class Branch {
    private static final Map<String, Branch> branchMap = new HashMap<>();

    public static void cleanUp() {
        branchMap.clear();
    }

    public static Branch register(Entry<?> entry, boolean required) {
        String name = entry.getName();
        return register(name, EnumBranchType.ENTRY, required);
    }

    public static Branch register(String id, EnumBranchType type, boolean required) {
        return register(id, type, required, null);
    }

    public static Branch register(String id, EnumBranchType type, boolean required, Object def) {
        if (!branchMap.containsKey(id)) {
            Branch value = new Branch(id, type, required, def);
            branchMap.put(id, value);
            return value;
        }

        Interactions.logger.warning("Exist registry: " + id);
        return branchMap.get(id);
    }

    @Nullable
    public static Branch getInstance(String id) {
        return branchMap.get(id);
    }

    @Nullable
    public static Branch getInstance(Entry<?> entry) {
        return branchMap.get(entry.getName());
    }

    @Nullable
    public static Branch[] getInstance(String... ids) {
        List<Branch> branches = new ArrayList<>();

        for (String id : ids) {
            Branch branch = branchMap.get(id);

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
                if (key.startsWith(branch.name)) {
                    switch (branch.getType()) {
                        case DOUBLE:
                            var = infoConfig.getDouble(key);
                            flag = true;
                            break;
                        case INT:
                            var = infoConfig.getInt(key);
                            flag = true;
                            break;
                        case STRING:
                            var = infoConfig.getString(key);
                            flag = true;
                            break;
                        case STRING_ARRAY:
                            var = infoConfig.getStringList(key).toArray(new String[0]);
                            flag = true;
                            break;
                        case BOOLEAN:
                            var = infoConfig.getBoolean(key);
                            flag = true;
                            break;
                        case VECTOR:
                            var = infoConfig.getVector(key);
                            flag = true;
                            break;
                        case SYSTEM:
                            var = infoConfig.get(key);
                            flag = true;
                            break;
                        case ENTRY: {
                            Entry<?> entry = Entry.getInstance(branch.name);
                            Object object = entry.getObject(infoConfig.getConfigurationSection(key));
                            if (object == null) {
                                available = false;
                            }

                            var = object;
                            flag = true;
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
                        Interactions.logger.warning("Illegal or missing value for branch: " + branch.name + ", value = " + var);
                        available = false;
                    }
                }
            }

            if (!flag) {
                if (branch.isRequired()) {
                    Interactions.logger.warning("Entry [" + infoConfig.getName() + "] missing key: " + branch.getName());
                    return null;
                } else {
                    switch (branch.getType()) {
                        case DOUBLE:
                            var = infoConfig.getDouble(branch.name, (Double) branch.def);
                            break;
                        case INT:
                            var = infoConfig.getInt(branch.name, (Integer) branch.def);
                            break;
                        case STRING:
                            var = infoConfig.getString(branch.name, (String) branch.def);
                            break;
                        case BOOLEAN:
                            var = infoConfig.getBoolean(branch.name, (Boolean) branch.def);
                            break;
                        case VECTOR:
                            var = infoConfig.getVector(branch.name, (Vector) branch.def);
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
    
    private final String name;
    private final EnumBranchType type;
    private final boolean required;
    private final Object def;
    private ILegalChecker legalChecker = null;

    public Branch(String name, EnumBranchType type, boolean required, Object def) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.def = def;
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
        DOUBLE, INT, STRING, STRING_ARRAY, BOOLEAN, VECTOR, SYSTEM, ENTRY, CONDITIONS;

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
