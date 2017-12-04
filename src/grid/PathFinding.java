package grid;

import inhabitant.Inhabitant;

import java.util.ArrayList;

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
}
