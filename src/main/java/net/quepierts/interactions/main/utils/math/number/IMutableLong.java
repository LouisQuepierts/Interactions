package net.quepierts.interactions.main.utils.math.number;

import org.jetbrains.annotations.NotNull;

public class IMutableLong extends IMutableNumber<Long> {
    private long value;

    public IMutableLong(long number) {
        value = number;
    }

    public IMutableLong(Number number) {
        value = number.longValue();
    }

    @Override
    public void increment() {
        ++ value;
    }

    @Override
    public void decrement() {
        -- value;
    }

    @Override
    public void add(Number number) {
        value += number.longValue();
    }

    @Override
    public void subtract(Number number) {
        value -= number.longValue();
    }

    @Override
    public void multiply(Number number) {
        value *= number.longValue();
    }

    @Override
    public void divide(Number number) {
        value /= number.longValue();
    }

    @Override
    public void setValue(Number number) {
        value = number.longValue();
    }

    @Override
    public Long value() {
        return value;
    }

    @Override
    public int compareTo(@NotNull Number o) {
        long other = o.longValue();
        return Long.compare(value, other);
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Long) {
            return value == (Long) obj;
        } else if (obj instanceof Number) {
            return value == ((Number) obj).longValue();
        }

        return false;
    }

    @Override
    public String toString() {
        return value + "L";
    }
}
