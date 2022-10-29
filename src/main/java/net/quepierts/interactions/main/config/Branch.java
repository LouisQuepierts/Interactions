package net.quepierts.interactions.main.config;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.data.invnetory.InventoryData;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public final class Branch {
    private static final Map<String, Branch> branchMap = new HashMap<>();

    public static void init() {
        register("amount", EnumBranchType.DOUBLE, true);
        register("chance", EnumBranchType.DOUBLE, true);

        register("duration", EnumBranchType.INT, true);
        register("level", EnumBranchType.INT, false, 0);

        register("compare", EnumBranchType.BOOLEAN, false, false);
        register("smaller", EnumBranchType.BOOLEAN, false, false);
        register("equal", EnumBranchType.BOOLEAN, false, false);
        register("percentage", EnumBranchType.BOOLEAN, false, false);
        register("isSet", EnumBranchType.BOOLEAN, false, false);

        register("name", EnumBranchType.STRING, true);
        register("slot", EnumBranchType.STRING, true);
        register("type", EnumBranchType.STRING, true);

        register("varAmountName", EnumBranchType.STRING, true);
        register("varTargetName", EnumBranchType.STRING, true);

        register("var", EnumBranchType.SYSTEM, true);
    }

    public static void register(Entry<?> entry, boolean required) {
        String name = entry.getName();
        if (!branchMap.containsKey(name)) {
            branchMap.put(name, new Branch(name, EnumBranchType.ENTRY, required));
        }
    }

    public static void register(String id, EnumBranchType type, boolean required) {
        if (!branchMap.containsKey(id)) {
            branchMap.put(id, new Branch(id, type, required));
        }
    }

    public static void register(String id, EnumBranchType type, boolean required, Object def) {
        if (!branchMap.containsKey(id)) {
            branchMap.put(id, new Branch(id, type, required, def));
        }
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

    public static List<Object> getBranchInfo(ConfigurationSection infoConfig, Branch[] branches) throws Exception {
        List<Object> var = new ArrayList<>();
        Set<String> keys = infoConfig.getKeys(false);

        branch:
        for (Branch branch : branches) {
            boolean flag = false;
            for (String key : keys) {
                if ("conditions".equalsIgnoreCase(key)) {
                    continue;
                }
                if (key.startsWith(branch.name) || key.equals(branch.name)) {
                    switch (branch.getType()) {
                        case DOUBLE:
                            var.add(infoConfig.getDouble(key));
                            flag = true;
                            break;
                        case INT:
                            var.add(infoConfig.getInt(key));
                            flag = true;
                            break;
                        case STRING:
                            String itemName = infoConfig.getString(key);
                            var.add(itemName);
                            flag = true;

                            if ("item".equals(key)) {
                                InventoryData.addItem(itemName);
                            }
                            break;
                        case BOOLEAN:
                            var.add(infoConfig.getBoolean(key));
                            flag = true;
                            break;
                        case SYSTEM:
                            var.add(infoConfig.get(key));
                            flag = true;
                            break;
                        case ENTRY: {
                            Entry<?> entry = Entry.getInstance(branch.name);
                            var.add(entry.getObject(infoConfig.getConfigurationSection(key)));
                            flag = true;
                            break;
                        }

                    }
                }
            }

            if (flag) {
                continue;
            }

            if (branch.isRequired()) {
                throw new Exception("Entry [" + infoConfig.getName() + "] missing key: " + branch.getName());
            } else {
                switch (branch.getType()) {
                    case DOUBLE:
                        var.add(infoConfig.getDouble(branch.name, (Double) branch.def));
                        break;
                    case INT:
                        var.add(infoConfig.getInt(branch.name, (Integer) branch.def));
                        break;
                    case STRING:
                        var.add(infoConfig.getString(branch.name, (String) branch.def));
                        break;
                    case BOOLEAN:
                        var.add(infoConfig.getBoolean(branch.name, (Boolean) branch.def));
                        break;
                    case SYSTEM:
                        var.add(infoConfig.get(branch.name));
                        break;
                    case ENTRY: {
                        Entry<?> entry = Entry.getInstance(branch.name);
                        var.add(entry.getObject(infoConfig.getConfigurationSection(branch.name)));
                        break;
                    }
                }
            }
        }


        return var;
    }
    
    private final String name;
    private final EnumBranchType type;
    private final boolean required;
    private final Object def;

    public Branch(String name, EnumBranchType type, boolean required, Object def) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.def = def;
    }

    public Branch(String name, EnumBranchType type, boolean required) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.def = null;
    }

    public String getName() {
        return name;
    }

    public EnumBranchType getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public enum EnumBranchType {
        DOUBLE, INT, STRING, BOOLEAN, SYSTEM, ENTRY, CONDITIONS;

        @Nullable
        public static EnumBranchType fromString(@Nonnull String name) {
            for (EnumBranchType value : EnumBranchType.values()) {
                if (value.name().equalsIgnoreCase(name)) {
                    return value;
                }
            }

            return null;
        }
    }
}
