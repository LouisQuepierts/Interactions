package net.quepierts.interactions.main.utils.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class RangeIterator {
    private final Vector vector;
    private final Location baseLoc;

    private int x;
    private int y;
    private int z;

    RangeIterator(Vector vector, Location location) {
        this.vector = vector;
        this.baseLoc = location;
        this.x = -vector.getBlockX();
        this.y = -vector.getBlockY();
        this.z = -vector.getBlockZ();
    }

    public boolean hasNext() {
        return x + 1 < vector.getBlockX() && y + 1 < vector.getBlockY() && z + 1 < vector.getBlockZ();
    }

    public Location next() {
        Location location = baseLoc.clone().add(x, y, z);
        if (x == vector.getBlockX()) {
            x = -vector.getBlockX();
            y ++;
            if (y > vector.getBlockY()) {
                y = -vector.getBlockY();
                z ++;
            }
        }
        return location;
    }
}
