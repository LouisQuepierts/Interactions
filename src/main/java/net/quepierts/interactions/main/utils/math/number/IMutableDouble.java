package net.quepierts.interactions.main.utils.math.number;

import org.jetbrains.annotations.NotNull;

public class IMutableDouble extends IMutableNumber<Double> {
    private double value;

    public IMutableDouble(double number) {
        value = number;
    }

    public IMutableDouble(Number number) {
        value = number.doubleValue();
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
        value += number.doubleValue();
    }

    @Override
    public void subtract(Number number) {
        value -= number.doubleValue();
    }

    @Override
    public void multiply(Number number) {
        value *= number.doubleValue();
    }

    @Override
    public void divide(Number number) {
        value /= number.doubleValue();
    }

    @Override
    public void setValue(Number number) {
        value = number.doubleValue();
    }

    @Override
    public Double value() {
        return value;
    }

    @Override
    public int compareTo(@NotNull Number o) {
        double other = o.doubleValue();
        return Double.compare(value, other);
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
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Double) {
            return value == (Double) obj;
        } else if (obj instanceof Number) {
            return value == ((Number) obj).doubleValue();
        }

        return false;
    }

    @Override
    public String toString() {
        return value + "D";
    }
}
