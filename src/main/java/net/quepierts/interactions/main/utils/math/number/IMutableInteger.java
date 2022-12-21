package net.quepierts.interactions.main.utils.math.number;

import org.jetbrains.annotations.NotNull;

public class IMutableInteger extends IMutableNumber<Integer> {
    private int value;

    public IMutableInteger(int number) {
        this.value = number;
    }

    public IMutableInteger(Number number) {
        this.value = number.intValue();
    }

    @Override
    public int compareTo(@NotNull Number o) {
        int other = o.intValue();
        return Integer.compare(value, other);
    }

    @Override
    public int intValue() {
        return value;
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
    public void increment() {
        ++ value;
    }

    @Override
    public void decrement() {
        -- value;
    }

    @Override
    public void add(Number number) {
        value += number.intValue();
    }

    @Override
    public void subtract(Number number) {
        value -= number.intValue();
    }

    @Override
    public void multiply(Number number) {
        value *= number.intValue();
    }

    @Override
    public void divide(Number number) {
        value /= number.intValue();
    }

    @Override
    public void setValue(Number number) {
        value = number.intValue();
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Integer) {
            return value == (Integer) obj;
        } else if (obj instanceof Number) {
            return value == ((Number) obj).intValue();
        }
        return false;
    }

    @Override
    public String toString() {
        return value + "i";
    }
}
