package grid;

import inhabitant.Inhabitant;

import java.util.Optional;

/**
 * File: grid.GridCell.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class GridCell {
    private Grid grid;
    private Location location;
    private Inhabitant inhabitant;
    private boolean searched = false;

    /**
     * Constructor for the GridCell
     * @param grid - reference to the parent grid
     * @param location - location of the current grid
     * @param inhabitant - inhabitant to place at the current GridCell
     */
    public GridCell(Grid grid, Location location, Inhabitant inhabitant) {
        this.grid = grid;
        this.location = location;
        this.inhabitant = inhabitant;
    }

    /**
     * Getter for the inhabitant of the given cell
     * @return Optional.empty if there is no inhabitant or
     *         Optional containing the inhabitant
     */
    public Optional<Inhabitant> getInhabitant() {
        return Optional.ofNullable(this.inhabitant);
    }

    /**
     * Setter for the {@link Inhabitant}
     * @param inhabitant - given inhabitant
     */
    public void setInhabitant(Inhabitant inhabitant){
        this.inhabitant = inhabitant;
    }

    /**
     * Remove the {@link Inhabitant} from the cell
     */
    public void removeInhabitant(){
        this.inhabitant = null;
    }

    /**
     * Getter for the searched boolean
     * @return whether or not this gridcell has been searched
     */
    public boolean isSearched() {
        return searched;
    }

    /**
     * Setter for searched boolean
     * @param searched
     */
    public void setSearched(boolean searched) {
        this.searched = searched;
    }

    /**
     * Determine if there is an {@link Inhabitant} at the given cell
     * @return boolean - whether or not there is an inhabitant present
     */
    public boolean isOccupied(){
        return getInhabitant().isPresent();
    }
}
