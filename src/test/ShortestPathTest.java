package test;

import grid.Location;
import grid.PathFinding;
import inhabitant.Inhabitant;

/**
 * File: ShortestPathTest.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class ShortestPathTest {
    public static void main(String[] args) {
        Inhabitant.Direction[] directions = PathFinding.shortestPath(new Location(0, 0), new Location(3, 3));
        for (Inhabitant.Direction direction:
              directions) {
            System.out.println(direction);
        }
    }
}
