package inhabitant;

import grid.Grid;
import grid.GridCell;
import grid.Location;
import resource.RobotNames;

import java.util.ArrayList;
import java.util.Random;

/**
 * File: inhabitant.BenignRobot.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class BenignRobot extends Inhabitant {

    private ArrayList<Inhabitant.Direction> moves;
    private int unitsMoved = 0;

    public BenignRobot(Grid grid, Location location) {
        super(grid, location);
        this.moves = new ArrayList<>();
        Random random = new Random();
        int t = random.nextInt(RobotNames.ROBOT_NAMES.length);
        this.name = RobotNames.ROBOT_NAMES[t];
        this.setStatus(Mode.SEARCHING);
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
    }

    public void executeNextMove(){
        Direction move = this.moves.get(0);
        if (move(move)){
            this.moves.remove(0);
        }
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
            case WEST:
                xNew = xOld - 1;
                yNew = yOld;
                break;
            case SOUTH:
                xNew = xOld;
                yNew = yOld + 1;
                break;
            case EAST:
                xNew = xOld + 1;
                yNew = yOld;
                break;
            case NORTH:
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
        if (this.getGrid().isValidLocation(xNew, yNew)) {
            // Location is out of bounds
            return false;
        }
        setLocation(new Location(xNew, yNew));
        GridCell newGC = this.getGrid().getGridCell(xNew, yNew);
        if (!newGC.isOccupied()){
            this.getGrid().getGridCell(xOld, yOld).removeInhabitant();
            newGC.setInhabitant(this);
            this.unitsMoved++;
            searchPerimeter();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Searches the perimeter of the current robot. Searches GridCells
     * that are North, South, East, and West. If a {@link Target} is found
     * the {@link #targetFound(int, int)} method is called to tell the other
     * Robots that the target has been found
     */
    private void searchPerimeter(){
        int x, y;
        x = this.getLocation().getX();
        y = this.getLocation().getY();
        if (getGrid().isValidLocation(x, y - 1)) {
            // North
            if (searchGridCell(getGrid().getGridCell(x, y - 1))){
                // Target present
                targetFound(x, y - 1);
            }
        }
        if (getGrid().isValidLocation(x, y + 1)) {
            // Sout
            if (searchGridCell(getGrid().getGridCell(x, y + 1))){
                // Target present
                targetFound(x, y + 1);
            }
        }
        if (getGrid().isValidLocation(x - 1, y)) {
            // West
            if (searchGridCell(getGrid().getGridCell(x - 1, y))){
                // Target present
                targetFound(x - 1, y);
            }
        }
        if (getGrid().isValidLocation(x + 1, y)) {
            // East
            if (searchGridCell(getGrid().getGridCell(x + 1, y))){
                // Target present
                targetFound(x + 1, y);
            }
        }
    }

    private void targetFound(int x, int y){

    }

    /**
     * Search GridCell searches a given GridCell for the target
     * @param gridCell GridCell to search
     * @return true if the GridCell contains the {@link Target}
     */
    private boolean searchGridCell(GridCell gridCell){
        if (gridCell.isOccupied()){
            if (gridCell.getInhabitant().get() instanceof Target){
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for unitsMoved
     * @return int - number of units moved
     */
    public int getUnitsMoved() {
        return unitsMoved;
    }

    /**
     * Created padding based on how long the robot name is
     * @param nameOfRobot the name of the robot
     * @return a String that is a number of spaces
     */
    private String getPadding(String nameOfRobot){
        String[] heros = RobotNames.ROBOT_NAMES;
        int longest = 0;
        for (int i = 0; i < heros.length; i++) {
            // Find the longest name
            if (heros[i].length() > longest)
                longest = heros[i].length();
        }
        int tabs = longest / 4;
        tabs++;
        int neededSize = (tabs * 4) + 1;
        int currentSize = nameOfRobot.length() + 2; // +2 for the single quotes
        int spacesNeeded = neededSize - currentSize;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * Like the toString method, just less info
     * @return 'Robot Name' @ (x, y)
     */
    public String info(){
        return "'" + this.name + "'" + getPadding(this.name) + "@ (" +
                this.getLocation().getX() + ", " +
                this.getLocation().getY() + ")";
    }

    @Override
    public String toString() {
        return "BenignRobot '" + this.name + "':\n" +
                "\tLocation: " + this.getLocation() + "\n" +
                "\tUnits Moved: " + this.unitsMoved + "\n" +
                "\tStatus: " + this.getStatus();
    }
}
