package inhabitant;

import grid.Grid;
import grid.GridCell;
import grid.Location;

import java.util.ArrayList;

/**
 * File: inhabitant.BenignRobot.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class BenignRobot extends Inhabitant {

    ArrayList<Inhabitant.Direction> moves;

    public BenignRobot(Grid grid, Location location) {
        super(grid, location);
        this.moves = new ArrayList<>();
    }

    @Override
    public void run() {

    }

    @Override
    public boolean isTarget() {
        return false;
    }

    public void addMove(Inhabitant.Direction direction){
        this.moves.add(0, direction);
        move(direction);
    }

    /**
     * moves the robot in one of the cardinal directions
     * @param direction {@link Inhabitant.Direction} (North, East, South, or West)
     * @return boolean - whether the move was able to be done
     */
    private boolean move(Inhabitant.Direction direction){
        this.moves.add(direction);
        int xOld = this.getLocation().getX();
        int yOld = this.getLocation().getY();
        int xNew, yNew;
        switch (direction){
            // Get the coordinates of the new location
            case NORTH:
                xNew = xOld - 1;
                yNew = yOld;
                break;
            case EAST:
                xNew = xOld;
                yNew = yOld + 1;
                break;
            case SOUTH:
                xNew = xOld + 1;
                yNew = yOld;
                break;
            case WEST:
                xNew = xOld;
                yNew = yOld - 1;
                break;
            default:
                xNew = -1;
                yNew = -1;
        }
        if (this.getGrid().getGridCell(xNew, yNew).getInhabitant().isPresent()){
            // The new location is already occupied
            return false;
        }
        int gridSize = this.getGrid().size();
        if (xNew < 0 || yNew < 0 || xNew >= gridSize || yNew >= gridSize){
            // The new position is out of bounds of the grid
            return false;
        }
        setLocation(new Location(xNew, yNew));
        GridCell newGC = this.getGrid().getGridCell(xNew, yNew);
        if (!newGC.isOccupied()){
            this.getGrid().getGridCell(xOld, yOld).removeInhabitant();
            newGC.setInhabitant(this);
            return true;
        } else {
            return false;
        }
    }
}
