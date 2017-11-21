package main;

import grid.Grid;
import inhabitant.BenignRobot;
import inhabitant.Inhabitant;

/**
 * File: main.RobotSimulation.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class RobotSimulation {
    private static Grid grid;

    public static void main(String[] args) {
        Grid grid = new Grid(10);
        RobotSimulation.grid = grid;
        grid.init(3);
        System.out.println(grid);
        ((BenignRobot) grid.inhabitants.get(0)).addMove(Inhabitant.Direction.NORTH);
        System.out.println(grid);
    }
}
