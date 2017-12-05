package test;

import grid.Grid;
import grid.Location;
import grid.PathFinding;
import inhabitant.BenignRobot;
import inhabitant.Inhabitant;

/**
 * File: SearchAlgorithmTest.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SearchAlgorithmTest {
    public static void main(String[] args) {
        Grid grid = new Grid(5, 5);
        grid.addInhabitant(new BenignRobot(grid, new Location(2, 2)), new Location(2, 2));
        Inhabitant.Direction[] moves = PathFinding.generateMovesToSearch(grid.getRobots().get(0).getLocation(), grid);
        for (Inhabitant.Direction direction :
                moves) {
            System.out.println(direction);

        }
        grid.getRobots().get(0).addMoves(moves);
        System.out.println(grid);
        while (true){
            grid.getRobots().get(0).executeNextMove();
            System.out.println(grid);
            System.out.println();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
