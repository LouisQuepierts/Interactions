package net.quepierts.interactions.main.config.loader;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.config.Entry;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConditionLoader {
    public static List<AbstractCondition> getConditions(ConfigurationSection config) {
        List<AbstractCondition> conditions = new ArrayList<>();
        Set<String> keys = config.getKeys(false);

        ConfigurationSection conditionConfig;

        Entry<?> entry;
        for (String key : keys) {
            boolean reverse = key.startsWith("!");
            String type = "condition_" + key.split("_")[0].replace("!", "");

            if (Entry.hasEntry(type)) {
                conditionConfig = config.getConfigurationSection(key);
                entry = Entry.getInstance(type);

                if (entry.isMultiRoots()) {
                    ActionLoader.setMultiTypes();
                }
                if (entry.getExecuteType() != null && !reverse) {
                    ActionLoader.setProcess_requiredExecutorType(entry.getExecuteType());
                }

                AbstractCondition condition = (AbstractCondition) entry.getObject(conditionConfig, reverse);
                if (condition != null) {
                    conditions.add(condition);
                }
            } else {
                Interactions.logger.warning("Unknown Condition: " + key);
            }
        }

        return conditions;
    }
}
