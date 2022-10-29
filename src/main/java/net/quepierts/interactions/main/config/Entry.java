package net.quepierts.interactions.main.config;

import net.quepierts.interactions.api.ICondition;
import net.quepierts.interactions.api.IExecutable;
import net.quepierts.interactions.main.data.action.ExecuteType;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;
import java.util.*;

public final class Entry<T> {
    private static final Map<String, Entry<?>> entryMap = new HashMap<>();

    public static boolean hasEntry(String id) {
        return entryMap.containsKey(id);
    }

    @Nullable
    public static Entry getInstance(String id) {
        return entryMap.get(id);
    }

    public static <T> Builder<T> getBuilder(String name) {
        return new Builder<>(name);
    }

    public static <T> Builder<T> getBuilder(Class<T> clazz) {
        if (IExecutable.class.isAssignableFrom(clazz)) {
            Builder<T> action = new Builder<>("action_" + clazz.getSimpleName().replace("Action", ""));
            return action;
        } else if (ICondition.class.isAssignableFrom(clazz)) {
            return new Builder<>("condition_" + clazz.getSimpleName().replace("Condition", ""));
        } else {
            return new Builder<>(clazz.getSimpleName());
        }
    }

    public static void register(String id, Entry<?> entry) {
        if (!entryMap.containsKey(id)) {
            entryMap.put(id, entry);
        }
    }

    private final String name;
    private IConstructor<T> constructor;

    private Branch[] branches;
    private ExecuteType executeType = null;

    private Entry(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public T getObject(Object... args) {
        return constructor.construct(args);
    }

    public T getObject(ConfigurationSection config) throws Exception {
        Object[] objects = Branch.getBranchInfo(config, branches).toArray(new Object[0]);
        return constructor.construct(objects);
    }

    public T getObject(ConfigurationSection config, Object... extraArgs) throws Exception {
        List<Object> branchInfo = Branch.getBranchInfo(config, branches);
        Collections.addAll(branchInfo, extraArgs);
        return constructor.construct(branchInfo.toArray(new Object[0]));
    }

    public ExecuteType getExecuteType() {
        return executeType;
    }

    public final static class Builder<T> {
        private final Entry<T> entry;
        private final List<Branch> branches = new ArrayList<>();

        private boolean built = false;
        private boolean asBranch = false;

        private Builder(String name) {
            entry = new Entry<>(name);
        }

        public Builder<T> addBranch(Branch branch) {
            branches.add(branch);
            return this;
        }

        public Builder<T> addBranch(Branch... branch) {
            Collections.addAll(branches, branch);
            return this;
        }

        public Builder<T> addBranch(String id) {
            branches.add(Branch.getInstance(id));
            return this;
        }

        public Builder<T> subEntry(Entry entry) {
            Branch.getInstance(entry);
            return this;
        }

        public Builder<T> executeType(ExecuteType type) {
            entry.executeType = type;
            return this;
        }

        public Builder<T> asBranch() {
            asBranch = true;
            return this;
        }

        public Builder<T> bind(IConstructor<T> iConstructor) {
            entry.constructor = iConstructor;
            return this;
        }

        public void register() {
            if (!built) {
                built = true;
                entry.branches = branches.toArray(new Branch[0]);

                entryMap.put(entry.getName(), entry);
                if (asBranch) {
                    Branch.register(entry, true);
                }
            }
        }
    }
}
