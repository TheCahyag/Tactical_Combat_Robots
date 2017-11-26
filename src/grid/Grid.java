package grid;

import inhabitant.BenignRobot;
import inhabitant.Inhabitant;
import inhabitant.Target;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * File: grid.Grid.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class Grid {
    private int xDim;
    private int yDim;
    private GridCell[][] gridCells;
    private ArrayList<Target> targetList;
    private ArrayList<BenignRobot> robotList;
    private int targets = 0;
    private int robots = 0;
    private int iterations = 0;

    private static char TARGET = 'T';
    private static char ROBOT = 'R';
    private static char EMPTY = '.';
    private static char SEARCHED = '*';

    /**
     * Constructor
     * @param xLen - length of the horizontal dimension
     * @param yLen - length of the vertical dimension
     */
    public Grid(int xLen, int yLen){
        this.targetList = new ArrayList<>();
        this.robotList = new ArrayList<>();
        this.xDim = xLen;
        this.yDim = yLen;
        this.gridCells = new GridCell[xLen][yLen];
        for (int i = 0; i < xLen; i++) {
            for (int j = 0; j < yLen; j++) {
                this.gridCells[i][j] = new GridCell(this, new Location(i, j), null);
            }
        }
    }

    /**
     * init initializes the grid with one target and a given number of robots
     * @param robots - number of {@link BenignRobot}s to add to the grid
     */
    public void init(int robots){
        Random random = new Random();

        // Put the Target on the Grid
        int x = random.nextInt(xDim);
        int y = random.nextInt(yDim);

        // Since the target is the first inhabitant to be put on the grid,
        // it is not necessary to check if the space is occupied
        Location location = new Location(x, y);
        this.addInhabitant(new Target(this, location), location);
        this.targets++;

        for (int i = 0; i < robots; i++) {
            // Put a number of robots on the grid
            x = random.nextInt(xDim);
            y = random.nextInt(yDim);
            GridCell gc = this.getGridCell(x, y);
            if (gc.getInhabitant().isPresent()){
                // GridCell already has a inhabitant
                i--;
            } else {
                // No inhabitant present, making new one
                location = new Location(x, y);
                this.addInhabitant(new BenignRobot(this, location), location);
                this.robots++;
            }
        }
    }

    /**
     * Getter for the array of grid cells
     * @return GridCell[][]
     */
    public GridCell[][] getGridCells() {
        return gridCells;
    }

    /**
     * Get an individual {@link GridCell} at a given x, y coordinate
     * @param x x coordinate
     * @param y y coordinate
     * @return Resulting {@link GridCell}
     */
    public GridCell getGridCell(int x, int y){
        return this.gridCells[x][y];
    }

    /**
     * Get the {@link GridCell} at a given location
     * @param location {@link Location}
     * @return {@link GridCell} the resulting GridCell
     */
    public GridCell getGridCell(Location location){
        return getGridCell(location.getX(), location.getY());
    }

    /**
     * Add an {@link Inhabitant} to the {@link Grid}
     * @param inhabitant Given {@link Inhabitant}
     * @param location {@link Location} to put
     */
    public void addInhabitant(Inhabitant inhabitant, Location location){
        if (inhabitant instanceof BenignRobot){
            this.robotList.add(((BenignRobot) inhabitant));
        } else {
            this.targetList.add(((Target) inhabitant));
        }
        GridCell gc = getGridCell(location);
        gc.setInhabitant(inhabitant);
        if (inhabitant instanceof BenignRobot)
            gc.setSearched(true);
    }

    /**
     * Determines if the location given is a valid location on the Grid
     * @param x x coor
     * @param y y coor
     * @return true if it is a valid location
     */
    public boolean isValidLocation(int x, int y){
        if (x < 0 || x >= xDim) {
            // X is out of bounds
            return false;
        }
        if (y < 0 || y >= yDim){
            // Y is out of bounds
            return false;
        }
        return true;
    }

    /**
     * Getter for the {@link Target}
     * @return Target
     */
    public Target getTarget() {
        return this.targetList.get(0);
    }

    /**
     * Getter for the Robot list
     * @return An ArrayList of BenignRobots
     */
    public ArrayList<BenignRobot> getRobots(){
        return this.robotList;
    }

    public void incrementIteration(){
        this.iterations++;
    }

    /**
     * Determines how much of the grid has
     * been searched by the robots as a percent
     * @return double - percent searched
     */
    private Double percentSearched(){
        double total = xDim * yDim;
        double searched = 0;
        for (int i = 0; i < xDim; i++) {
            for (int j = 0; j < yDim; j++) {
                if (gridCells[i][j].isSearched())
                    searched++;
            }
        }
        return (searched / total) * 100;
    }

    /**
     * Return statistics about the current Grid in a String format
     * @return
     */
    public String getStatistics(){
        int totalUnitsMoved = 0;
        for (BenignRobot robot :
                this.robotList) {
            totalUnitsMoved += robot.getUnitsMoved();
        }
        return "Grid (" + xDim + "x" + yDim + "): \n" +
                "\tTargets:             " + this.targetList.size() + "\n" +
                "\tBenign Robots:       " + this.robotList.size() + "\n" +
                "\t% Searched:          " + percentSearched().shortValue() + "%\n" +
                "\tIterations:          " + this.iterations + "\n" +
                "\tTotal Units Moved:   " + totalUnitsMoved + "\n" +
                "\tAverage Units Moved: " + (totalUnitsMoved / this.robotList.size());
    }

    @Override
    public String toString() {
        GridCell[][] gridCells = this.getGridCells();

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < yDim; i++) {
            for (int j = 0; j < xDim; j++) {
                char c;
                GridCell gridCell = gridCells[j][i];
                Optional<Inhabitant> inhabitantOptional = gridCell.getInhabitant();
                c = inhabitantOptional.map(inhabitant ->
                        inhabitant instanceof BenignRobot ? ROBOT : TARGET).orElse(gridCell.isSearched() ? SEARCHED : EMPTY);
                result.append(c).append(" ");
            }
            if (i + 1 < xDim)
                result.append("\n");
        }
        return result.toString();
    }

}
