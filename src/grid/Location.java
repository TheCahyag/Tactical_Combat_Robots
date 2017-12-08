package grid;

import inhabitant.Inhabitant;

/**
 * File: grid.Location.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location getNorth(){
        return new Location(this.getX(), this.getY() - 1);
    }

    public Location getSouth(){
        return new Location(this.getX(), this.getY() + 1);
    }

    public Location getWest(){
        return new Location(this.getX() - 1, this.getY());
    }

    public Location getEast(){
        return new Location(this.getX() + 1, this.getY());
    }

    public Location getDirection(Inhabitant.Direction direction){
        switch (direction) {
            case EAST:
                return getEast();
            case WEST:
                return getWest();
            case NORTH:
                return getNorth();
            case SOUTH:
                return getSouth();
            default:
                return null;
        }
    }

    public static Inhabitant.Direction getClockWiseDirection(Inhabitant.Direction direction){
        switch (direction) {
            case EAST:
                return Inhabitant.Direction.SOUTH;
            case WEST:
                return Inhabitant.Direction.NORTH;
            case NORTH:
                return Inhabitant.Direction.EAST;
            case SOUTH:
                return Inhabitant.Direction.WEST;
            default:
                return null;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (x != location.x) return false;
        return y == location.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + (x + 1) + ", " + (y + 1) + ")";
        // +1s so the coordinates start at one instead of 0
    }
}
