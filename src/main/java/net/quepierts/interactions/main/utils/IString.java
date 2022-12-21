package net.quepierts.interactions.main.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IString implements IField {
    private final String value;

    public static IString get(String value) {
        char c = value.charAt(0);
        switch (c) {
            case '\"': case '\'':
                return new IString(value.replaceAll(String.valueOf(c), ""));
            case '-':
                return new IPointedString(value);
        }

        return new IString(value);
    }

    public IString() {
        this.value = null;
    }

    public IString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public void update(@NotNull Player player) {}
}
