package inhabitant;

import grid.Grid;
import grid.Location;

/**
 * File: Target.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class Target extends Inhabitant {

    public Target(Grid grid, Location location) {
        super(grid, location);
    }

    @Override
    public void run() {

    }

    @Override
    public boolean isTarget() {
        return true;
    }
}
