package grid;

import inhabitant.Inhabitant;

import java.util.ArrayList;

/**
 * File: grid.Grid.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class Grid {
    private int size;
    private GridCell[][] gridCells;
    public ArrayList<Inhabitant> inhabitants;

    /**
     * 
     * @param size
     */
    public Grid(int size){
        this.inhabitants = new ArrayList<>();
        this.size = size;
        this.gridCells = new GridCell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.gridCells[i][j] = new GridCell(this, new Location(i, j), null);
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
        this.inhabitants.add(inhabitant);
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
}
