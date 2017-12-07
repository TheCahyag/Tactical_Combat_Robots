package grid;

import inhabitant.Inhabitant;

import java.util.ArrayList;
import java.util.Optional;

/**
 * File: PathFinding.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class PathFinding {

    /**
     * Finds the shorts path from one location to another and
     * returns the list of moves to get to that position
     * @param start starting {@link Location}
     * @param end desired {@link Location}
     * @return Array of Directions that server as moves to get from the start
     * location to the end location, where Directions[0] is the first move to take
     */
    public static Inhabitant.Direction[] shortestPath(Location start, Location end){
        ArrayList<Inhabitant.Direction> directions = new ArrayList<>();

        // Vertical moves
        if (start.getX() > end.getX()){
            // Need to move West
            int moves = start.getX() - end.getX();
            for (int i = 0; i < moves; i++) {
                directions.add(Inhabitant.Direction.WEST);
            }
        } else if (start.getX() < end.getX()){
            // Need to move East
            int moves = end.getX() - start.getX();
            for (int i = 0; i < moves; i++) {
                directions.add(Inhabitant.Direction.EAST);
            }
        }

        // Horizontal moves
        if (start.getY() > end.getY()){
            // Need to move North
            int moves = start.getY() - end.getY();
            for (int i = 0; i < moves; i++) {
                directions.add(Inhabitant.Direction.NORTH);
            }
        } else if (start.getY() < end.getY()){
            // Need to move South
            int moves = end.getY() - start.getY();
            for (int i = 0; i < moves; i++) {
                directions.add(Inhabitant.Direction.SOUTH);
            }
        }
        Inhabitant.Direction[] moves = new Inhabitant.Direction[directions.size()];
        directions.toArray(moves);
        return moves;
    }

    /**
     * Generates the moves that a {@link inhabitant.BenignRobot} will take to passively search
     * the grid. The method will not generate moves that cannot be taken by the Robot.
     *
     * The basic pattern of the movements generate follow a clockwise spiral (keeping in mind
     * the robot can search cells it is adjacent to).
     *
     * Imagine a 5x5 grid with a robot at the center:
     *  .-.-.-.-.
     *  |
     *  . . . . .
     *  |
     *  . . R-.-.
     *  |       |
     *  . . . . .
     *  |       |
     *  .-.-.-.-.
     * The line indicates the path that would be generated for a robot in the following 5x5.
     * The list of directions includes more then just the path described above, after making
     * the moves displayed above, it will have locations that move the robot along the edge of
     * grid, but in the actual simulation these moves won't be taken since after a robot searches
     * 0 gridcells it will remove all of its moves and find a path to the closest unsearched
     * gridcell.
     *
     * @param startingLocation - Starting location of the robot
     * @param grid - given {@link Grid} of the simulation
     * @return Primitive Array of Directions
     */
    public static Inhabitant.Direction[] generateMovesToSearch(Location startingLocation, Grid grid){
        ArrayList<Inhabitant.Direction> directions = new ArrayList<>();
        Location currentLocation = new Location(startingLocation.getX(), startingLocation.getY());
        // Init
        Inhabitant.Direction direction = Inhabitant.Direction.EAST;
        if (grid.isValidLocation(currentLocation.getDirection(direction))){
            currentLocation = currentLocation.getDirection(direction);
            directions.add(direction);
        }
        if (grid.isValidLocation(currentLocation.getDirection(direction))){
            currentLocation = currentLocation.getDirection(direction);
            directions.add(direction);
        }
        direction = Location.getClockWiseDirection(direction);

        if (grid.isValidLocation(currentLocation.getDirection(direction))){
            currentLocation = currentLocation.getDirection(direction);
            directions.add(direction);
        }
        if (grid.isValidLocation(currentLocation.getDirection(direction))){
            currentLocation = currentLocation.getDirection(direction);
            directions.add(direction);
        }
        direction = Location.getClockWiseDirection(direction);
        int dim = grid.getxDim() > grid.getyDim() ? grid.getxDim() : grid.getyDim();
        for (int i = 2; i < dim; i++) {
            for (int j = 0; j < (i * 2) + 1; j++) {
                if (grid.isValidLocation(currentLocation.getDirection(direction))) {
                    directions.add(direction);
                    currentLocation = currentLocation.getDirection(direction);
                } else {
                    break;
                }
            }
            direction = Location.getClockWiseDirection(direction);
            for (int j = 0; j < (i * 2) + 1; j++) {
                if (grid.isValidLocation(currentLocation.getDirection(direction))) {
                    directions.add(direction);
                    currentLocation = currentLocation.getDirection(direction);
                } else {
                    break;
                }
            }
            direction = Location.getClockWiseDirection(direction);
        }
        Inhabitant.Direction[] moves = new Inhabitant.Direction[directions.size()];
        directions.toArray(moves);
        return moves;
    }

    /**
     * Distance from returns the number of moves it would take to get from one location to another
     * @param start start location
     * @param end end location
     * @return int - number of moves it would take to get from one location to the other
     */
    public static int distanceFrom(Location start, Location end){
        return shortestPath(start, end).length;
    }

    /**
     * Find the closest unsearched GridCell
     * @param home - location of the object looking for a gridcell
     * @param grid - grid
     * @return Optional of a Location, given that there is an unsearched cell in the given grid
     */
    public static Optional<Location> closestUnsearched(Location home, Grid grid){
        int shortest = -1;
        Location locOfShortest = null;
        for (int i = 0; i < grid.getxDim(); i++) {
            for (int j = 0; j < grid.getyDim(); j++) {
                int dist = distanceFrom(home, new Location(i, j));
                if (!grid.getGridCell(i, j).isSearched()){
                    // GridCell has not been searched
                    if (shortest == -1 || shortest > dist){
                        shortest = dist;
                        locOfShortest = new Location(i, j);
                    }
                }
            }
        }
        return Optional.ofNullable(locOfShortest);
    }
}
