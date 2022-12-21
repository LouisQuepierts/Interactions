package net.quepierts.interactions.main.utils.math.threshold;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.utils.IField;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Threshold<T extends Number> implements IField {
    private final IMutableNumber<?> value;
    private boolean smaller;
    private boolean equal;

    public static boolean isInteger(Threshold<?> threshold) {
        return threshold.value.getTypeClass().isAssignableFrom(Integer.class);
    }

    public static boolean isFloat(Threshold<?> threshold) {
        return threshold.value.getTypeClass().isAssignableFrom(Float.class);
    }

    public static Threshold<?> cast(Object o, Class<? extends Number> type) throws Exception {
        if (o instanceof Threshold) {
            Threshold<?> threshold = (Threshold<?>) o;

            if (threshold.value.getTypeClass().equals(type)) {
                return threshold;
            }

            Interactions.logger.warning("Required Type: " + type.getSimpleName() + ", Threshold Type: " + threshold.value.getTypeClass().getSimpleName());
        }

        throw new Exception();
    }

    @SafeVarargs
    public static Threshold<?> cast(Object o, Class<? extends Number>... types) throws Exception {
        if (o instanceof Threshold) {
            Threshold<?> threshold = (Threshold<?>) o;

            if (Arrays.asList(types).contains(threshold.value.getTypeClass())) {
                return threshold;
            }

            StringBuilder builder = new StringBuilder("Required Types: [");
            for (Class<?> type : types) {
                builder.append(type.getSimpleName()).append(", ");
            }

            builder.append("], Threshold Type: ").append(threshold.value.getTypeClass().getSimpleName());
            Interactions.logger.warning(builder.toString());
        }

        throw new Exception();
    }

    public Threshold(Object[] args) {
        this.value = (IMutableNumber<?>) args[0];
        this.smaller = (boolean) args[1];
        this.equal = (boolean) args[2];
    }

    public Threshold(IMutableNumber<T> number, boolean smaller, boolean equal) {
        this.value = number;
        this.smaller = smaller;
        this.equal = equal;
    }

    public void setValue(Number number) {
        this.value.setValue(number);
    }

    public void setSmaller(boolean smaller) {
        this.smaller = smaller;
    }

    public void switchSmaller() {
        smaller = !smaller;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    public void switchEqual() {
        equal = !equal;
    }

    public boolean compare(@NotNull Number o) {
        int compare = this.compareTo(o);
        return (equal && (compare == 0)) || smaller == (compare > 0);
    }

    public int compareTo(@NotNull Number o) {
        return value.compareTo(o);
    }

    public boolean equals(@NotNull Number o) {
        return value.equals(o);
    }

    @Override
    public String toString() {
        return String.format("{value = %s, smaller = %s, equal = %s}", value.toString(), smaller, equal);
    }

    @Override
    public void update(@NotNull Player player) {
        value.update(player);
    }
}
