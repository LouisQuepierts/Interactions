package net.quepierts.interactions.main.utils.math.number;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.utils.IField;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class IMutableNumber<T extends Number> extends Number implements Comparable<Number>, IField {
    public static boolean isNumber(@NotNull String arg) {
        char type = arg.toLowerCase().charAt(arg.length() - 1);
        String number = arg.substring(0, arg.length() - 1);

        try {
            if ('0' <= type && '9' >= type) {
                if (arg.contains(".")) {
                    Double.parseDouble(arg);
                } else {
                    Integer.parseInt(arg);
                }
            } else {
                switch (type) {
                    case 'd':
                        Double.parseDouble(number);
                        break;
                    case 'f':
                        Float.parseFloat(number);
                        break;
                    case 'i':
                        Integer.parseInt(number);
                        break;
                    case 'l':
                        Long.parseLong(number);
                        break;
                    default:
                        return false;
                }
            }
        } catch (Exception ignored) {
            return false;
        }

        return true;
    }

    @Nullable
    public static IMutableNumber<?> get(String arg) {
        try {
            if (arg.startsWith("-&")) {
                return new IPointedNumber(arg);
            }

            char type = arg.toLowerCase().charAt(arg.length() - 1);
            String number = arg.substring(0, arg.length() - 1);

            if ('0' <= type && '9' >= type) {
                if (arg.contains(".")) {
                    return new IMutableDouble(Double.parseDouble(arg));
                } else {
                    return new IMutableInteger(Integer.parseInt(arg));
                }
            } else {
                switch (type) {
                    case 'd':
                        return new IMutableDouble(Double.parseDouble(number));
                    case 'f':
                        return new IMutableFloat(Float.parseFloat(number));
                    case 'i':
                        return new IMutableInteger(Integer.parseInt(number));
                    case 'l':
                        return new IMutableLong(Long.parseLong(number));
                }
            }
        } catch (Exception ignored) {
            Interactions.logger.warning("Illegal number value: " + arg);
        }

        return null;
    }

    public static IMutableNumber<?> cast(@NotNull Object o, Class<? extends Number> type) throws Exception {
        if (o instanceof IMutableNumber) {
            IMutableNumber<?> number = (IMutableNumber<?>) o;

            if (!number.getTypeClass().equals(type)) {
                Interactions.logger.warning("Suggest Type: " + type.getSimpleName() + ", Uses Type: " + number.getTypeClass().getSimpleName());
            }

            return number;
        }

        throw new Exception();
    }

    @SafeVarargs
    public static @NotNull IMutableNumber<?> cast(@NotNull Object o, Class<? extends Number>... types) throws Exception {
        if (o instanceof IMutableNumber) {
            IMutableNumber<?> number = (IMutableNumber<?>) o;

            if (!(types.length == 0 || number.value() instanceof IMutableNumber || Arrays.asList(types).contains(number.getTypeClass()))) {
                StringBuilder builder = new StringBuilder("Suggest Types: [");
                for (Class<?> type : types) {
                    builder.append(type.getSimpleName()).append(", ");
                }

                builder.append("], Uses Type: ").append(number.getTypeClass().getSimpleName());
                Interactions.logger.warning(builder.toString());
            }

            return number;
        }

        return null;
    }

    @Nullable
    public static Number getNumber(@NotNull String arg) {
        char type = arg.toLowerCase().charAt(arg.length() - 1);
        String number = arg.substring(0, arg.length() - 1);

        try {
            if ('0' <= type && '9' >= type) {
                if (arg.contains(".")) {
                    return Double.parseDouble(arg);
                } else {
                    return Integer.parseInt(arg);
                }
            } else {
                switch (type) {
                    case 'd':
                        return Double.parseDouble(number);
                    case 'f':
                        return Float.parseFloat(number);
                    case 'i':
                        return Integer.parseInt(number);
                    case 'l':
                        return Long.parseLong(number);
                }
            }
        } catch (Exception ignored) {
            Interactions.logger.warning("Illegal number value: " + arg);
        }

        return null;
    }

    @Nullable
    public static IMutableNumber<?> get(@NotNull Number number) {
        if (number instanceof Double) {
            return new IMutableDouble(number);
        } else if (number instanceof Float) {
            return new IMutableFloat(number);
        } else if (number instanceof Integer || number instanceof Byte) {
            return new IMutableInteger(number);
        } else if (number instanceof Long) {
            return new IMutableLong(number);
        }

        return null;
    }

    public static String parseString(@NotNull Number number) {
        char type = 'd';

        if (number instanceof Float) {
            type = 'f';
        } else if (number instanceof Integer) {
            type = 'i';
        } else if (number instanceof Long) {
            type = 'l';
        }

        return number.toString() + type;
    }

    public final Class<? extends Number> getTypeClass() {
        return value().getClass();
    }

    public abstract void increment();

    public abstract void decrement();

    public abstract void add(Number number);

    public abstract void subtract(Number number);

    public abstract void multiply(Number number);

    public abstract void divide(Number number);

    public abstract void setValue(Number number);

    public abstract T value();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Number) {
            return obj.equals(this);
        }

        return false;
    }

    @Override
    public void update(@NotNull Player player) {

    }
}
