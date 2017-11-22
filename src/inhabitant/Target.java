package inhabitant;

import grid.Grid;
import grid.Location;
import resource.TargetNames;

import java.util.Random;

/**
 * File: Target.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class Target extends Inhabitant {

    public Target(Grid grid, Location location) {
        super(grid, location);
        Random random = new Random();
        int t = random.nextInt(TargetNames.TARGET_NAMES.length);
        this.name = TargetNames.TARGET_NAMES[t];
    }

    @Override
    public void run() {

    }

    @Override
    public boolean isTarget() {
        return true;
    }

    @Override
    public String toString() {
        return "Target '" + this.name + "' has a location of (" + this.getLocation().getX() + ", " + this.getLocation().getY() + ")";
    }
}
