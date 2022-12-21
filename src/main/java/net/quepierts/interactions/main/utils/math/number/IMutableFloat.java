package net.quepierts.interactions.main.utils.math.number;

import org.jetbrains.annotations.NotNull;

public class IMutableFloat extends IMutableNumber<Float> {
    private float value;
    
    public IMutableFloat(float number) {
        value = number;
    }
    
    public IMutableFloat(Number number) {
        value = number.floatValue();
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
        value += number.floatValue();
    }

    @Override
    public void subtract(Number number) {
        value -= number.floatValue();
    }

    @Override
    public void multiply(Number number) {
        value *= number.floatValue();
    }

    @Override
    public void divide(Number number) {
        value /= number.floatValue();
    }

    @Override
    public void setValue(Number number) {
        value = number.floatValue();
    }

    @Override
    public Float value() {
        return value;
    }

    @Override
    public int compareTo(@NotNull Number o) {
        float other = o.floatValue();
        return Float.compare(value, other);
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return (long) value;
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
        if (obj instanceof Float) {
            return value == (Float) obj;
        } else if (obj instanceof Number) {
            return value == ((Number) obj).floatValue();
        }
        return false;
    }

    @Override
    public String toString() {
        return value + "d";
    }
}
