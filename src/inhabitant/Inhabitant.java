package inhabitant;

import grid.Grid;
import grid.Location;
import message.Message;
import message.Messageable;

import java.util.Observer;

/**
 * File: inhabitant.Inhabitant.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public abstract class Inhabitant implements Runnable, Observer, Messageable {
    private Grid grid;
    private Location location;
    private Mode status;
    String name; // Set in sub-class

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

    @Override
    public abstract void receiveMessage(Message message);

    public String getName(){
        return this.name;
    }

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
