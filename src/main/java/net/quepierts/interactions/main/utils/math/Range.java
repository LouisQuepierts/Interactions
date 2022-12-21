package net.quepierts.interactions.main.utils.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Range {
    private final boolean enable;
    private final Vector vector;

    public Range(boolean enable, Vector vector) {
        this.enable = enable;
        this.vector = vector;
    }

    public Location[] getLocations(Location location) {
        Location[] locations = new Location[getLocationAmount()];
        int i = 0;
        for (int x = -vector.getBlockX(); x < vector.getBlockX(); x++) {
            for (int y = -vector.getBlockY(); y < vector.getBlockY(); y++) {
                for (int z = -vector.getBlockZ(); z < vector.getBlockZ(); z++) {
                    locations[i] = location.add(x, y, z);
                    ++i;
                }
            }
        }

        return locations;
    }

    public boolean isEnable() {
        return enable;
    }

    public int getLocationAmount() {
        return (vector.getBlockX() * 2 - 1) * (vector.getBlockY() * 2 - 1) * (vector.getBlockZ() * 2 - 1);
    }

    public RangeIterator iterator(Location location) {
        return new RangeIterator(vector, location);
    }

}
