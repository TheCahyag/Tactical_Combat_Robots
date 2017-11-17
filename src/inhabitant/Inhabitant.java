package inhabitant;

import grid.Grid;

/**
 * File: inhabitant.Inhabitant.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public abstract class Inhabitant implements Runnable {
    private Grid grid;
    private String name;
    private int posX;
    private int posY;
    private int unitsMoved = 0;
    private Mode Status;

    public static enum Mode {
        SEARCHING,
        PURSUING,
        IN_POSITION
    }

    @Override
    public abstract void run();
}
