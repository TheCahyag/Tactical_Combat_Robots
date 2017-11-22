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
    private int size;
    private GridCell[][] gridCells;
    private ArrayList<Target> targetList;
    private ArrayList<BenignRobot> robotList;
    private int targets = 0;
    private int robots = 0;

    private static char TARGET = 'T';
    private static char ROBOT = 'R';
    private static char EMPTY = '.';

    /**
     *
     * @param size
     */
    public Grid(int size){
        this.targetList = new ArrayList<>();
        this.robotList = new ArrayList<>();
        this.size = size;
        this.gridCells = new GridCell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.gridCells[i][j] = new GridCell(this, new Location(i, j), null);
            }
        }
    }

    /**
     * init intialilizes the grid with one target and a given number of robots
     * @param robots - number of {@link BenignRobot}s to add to the grid
     */
    public void init(int robots){
        Random random = new Random();
        int size = this.size();
        // Put the Target on the Grid
        int x = random.nextInt(size);
        int y = random.nextInt(size);

        // Since the target is the first inhabitant to be put on the grid,
        // it is not necessary to check if the space is occupied
        Location location = new Location(x, y);
        this.addInhabitant(new Target(this, location), location);
        this.targets++;

        for (int i = 0; i < robots; i++) {
            // Put a number of robots on the grid
            x = random.nextInt(size);
            y = random.nextInt(size);
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
    }

    /**
     * Dimension of the {@link Grid}
     * @return int - dimension of the grid
     */
    public int size(){
        return this.size;
    }

    @Override
    public String toString() {
        GridCell[][] gridCells = this.getGridCells();
        int size = gridCells.length;

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char c;
                GridCell gridCell = gridCells[i][j];
                Optional<Inhabitant> inhabitantOptional = gridCell.getInhabitant();
                c = inhabitantOptional.map(inhabitant ->
                        inhabitant instanceof BenignRobot ? ROBOT : TARGET).orElse(EMPTY);
                result.append(c).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

}
