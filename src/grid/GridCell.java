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
}
