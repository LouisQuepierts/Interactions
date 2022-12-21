package net.quepierts.interactions.main.data;

import net.quepierts.interactions.Interactions;

import java.text.Collator;
import java.util.*;

public class Registry<T> {
    private final Map<String, T> registry;
    private final String name;
    private boolean enable;

    public Registry(String name) {
        this.registry = new TreeMap<>();

        this.name = name;
    }

    public T register(String id, T value) {
        if (enable) {
            if (!registry.containsKey(id)) {
                registry.put(id, value);
            } else {
                Interactions.logger.info("Exist ID: " + id);
            }

            return registry.get(id);
        } else {
            Interactions.logger.warning("Registry " + name + "Is Closed");
        }

        return null;
    }

    public T get(String id) {
        return registry.get(id);
    }

    public boolean contains(String id) {
        return registry.containsKey(id);
    }

    public void close() {
        this.enable = false;

        List<Map.Entry<String, T>> entries = new ArrayList<>(registry.entrySet());
        entries.sort(Map.Entry.comparingByKey((o1, o2) -> Collator.getInstance(Locale.UK).compare(o1, o2)));
    }

    public void cleanUp() {
        this.registry.clear();
        this.enable = true;
    }

    public boolean enable() {
        return enable;
    }
}
