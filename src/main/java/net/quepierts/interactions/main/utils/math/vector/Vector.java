package net.quepierts.interactions.main.utils.math.vector;

import net.quepierts.interactions.main.utils.IField;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Vector implements IField {
    protected final IMutableNumber<?> x;
    protected final IMutableNumber<?> y;
    protected final IMutableNumber<?> z;

    public Vector() {
        this(0);
    }

    public Vector(Number value) {
        this.x = IMutableNumber.get(value);
        this.y = IMutableNumber.get(value);
        this.z = IMutableNumber.get(value);
    }

    public Vector(Number x, Number y, Number z) {
        this.x = IMutableNumber.get(x);
        this.y = IMutableNumber.get(y);
        this.z = IMutableNumber.get(z);
    }

    public Vector(IMutableNumber<?> x, IMutableNumber<?> y, IMutableNumber<?> z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public Location addToLocation(Location location) {
        return location.add(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }

    @Override
    public void update(@NotNull Player player) {
        x.update(player);
        y.update(player);
        z.update(player);
    }
}
