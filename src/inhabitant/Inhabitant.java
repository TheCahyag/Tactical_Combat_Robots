package inhabitant;

import grid.Grid;
import grid.Location;

/**
 * File: inhabitant.Inhabitant.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public abstract class Inhabitant implements Runnable {
    private Grid grid;
    private Location location;
    private int unitsMoved = 0;
    private Mode status;

    /**
     * Do nothing constructor does nothing
     */
    public Inhabitant(){}

    public Inhabitant(Grid grid, Location location){
        this.grid = grid;
        this.location = location;
    }

    /**
     * Mode defines what the inhabitant is currently doing.
     * SEARCHING - Searching for the target, if the inhabitant is the target it is defaulted to IN_POSITION
     */
    public static enum Mode {
        SEARCHING,
        PURSUING,
        IN_POSITION
    }

    /**
     * Cardinal directions
     */
    public static enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    @Override
    public abstract void run();

    /**
     *
     * @return
     */
    public abstract boolean isTarget();

    public Grid getGrid() {
        return grid;
    }

    public Location getLocation() {
        return location;
    }

    public Mode getStatus() {
        return status;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStatus(Mode status) {
        this.status = status;
    }
}
