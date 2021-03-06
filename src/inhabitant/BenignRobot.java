package inhabitant;

import grid.Grid;
import grid.GridCell;
import grid.Location;
import grid.PathFinding;
import main.RobotSimulation;
import message.Message;
import message.TargetFoundMessage;
import resource.RobotNames;

import java.util.*;

/**
 * File: inhabitant.BenignRobot.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class BenignRobot extends Inhabitant {

    private ArrayList<Inhabitant.Direction> moves;
    private int unitsMoved = 0;
    private int turnsSinceMove = 0;
    private int cellsSearchedThisIteration = 0;
    private GridCell gridCellToSearch = null;
    private ArrayList<Message> messages;
    private boolean hasMovedThisIteration = false;

    /**
     * BenignRobot constructor, inits a bunch of stuff
     * @param grid - {@link Grid} for the simulation
     * @param location - {@link Location} of this new robot
     */
    public BenignRobot(Grid grid, Location location) {
        super(grid, location);
        this.moves = new ArrayList<>();
        Random random = new Random();
        int t = random.nextInt(RobotNames.ROBOT_NAMES.length);
        this.name = RobotNames.ROBOT_NAMES[t];
        this.setStatus(Mode.PASSIVE_SEARCHING);
        this.messages = new ArrayList<>();
        grid.addObserver(this);
    }

    @Override
    public void run() {
        while (RobotSimulation.running){
            synchronized (getGrid()){
                try {
                    getGrid().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (this.messages.size() != 0) {
                    // Check for messages
                    ListIterator<Message> messageListIterator = this.messages.listIterator();
                    while (messageListIterator.hasNext()){
                        handleMessage(messageListIterator.next());
                    }
                    this.messages = new ArrayList<>();
                }
                // Reset values
                this.cellsSearchedThisIteration = 0;
                this.hasMovedThisIteration = false;
                executeNextMove();
                switch (getStatus()){
                    case PASSIVE_SEARCHING:
                        if (this.cellsSearchedThisIteration == 0 && this.hasMovedThisIteration){
                            // Transition into an active search mode
                            activeSearching();
                        }
                        break;
                    case ACTIVE_SEARCHING:
                        if (this.moves.size() == 0){
                            // The robot is out of moves and needs more
                            findUnsearchedCell();
                        }
                        if (this.gridCellToSearch != null){
                            if (this.gridCellToSearch.isSearched()) {
                                // The cell they were looking for got searched by another robot
                                purgeMoves();
                                findUnsearchedCell();
                            }
                        }
                        break;
                    case PURSUING:
                        if (this.turnsSinceMove > 3){
                            setStatus(Mode.IN_POSITION);
                            purgeMoves();
                            // TODO change^^
                        }
                        break;
                    case IN_POSITION:
                        break;
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO
    }

    @Override
    public void receiveMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * Parse a {@link Message} which could be a {@link TargetFoundMessage} or another message
     * @param message - message to get information from
     */
    private void handleMessage(Message message){
        if (message instanceof TargetFoundMessage){
            // Remove the future moves
            purgeMoves();

            // Set status
            this.setStatus(Mode.PURSUING);

            // Set moves to get to target
            Location target = ((TargetFoundMessage) message).parseMessage();
            purgeMoves();
            this.addMoves(PathFinding.shortestPath(getLocation(), target));
        }
    }

    /* START: Target methods */

    /**
     * Print out a message saying the target was found and send a
     * message to all robots (including this robot) telling them
     * to pursue the target at the given location
     * @param x x coor of the target
     * @param y y coor of the target
     */
    private void targetFound(int x, int y){
        Target target = ((Target) getGrid().getGridCell(x, y).getInhabitant().get());

        System.out.println("Robot '" + this.name + "' has found Target '"
                + target.getName() + "' @ " + target.getLocation());
        ArrayList<BenignRobot> robots = getGrid().getRobots();
        for (int i = 0; i < robots.size(); i++) {
            new TargetFoundMessage(this, robots.get(i), new Location(x, y)).sendMessage();
        }
    }

    @Override
    public boolean isTarget() {
        return false;
    }

    /* END: Target methods */

    /* START: Moving methods */

    /**
     * Remove all moves from the moves list
     */
    private void purgeMoves(){
        this.moves = new ArrayList<>();
    }

    /**
     * Add a number of moves to the robot's internal moves list
     * @param directions Primitive array of Directions
     */
    public void addMoves(Direction[] directions){
        for (Inhabitant.Direction direction :
                directions) {
            addMove(direction);
        }
    }

    /**
     * Add a move to the Robot's move queue
     * @param direction - given direction to add
     */
    public void addMove(Inhabitant.Direction direction){
        this.moves.add(direction);
    }

    /**
     * Attempts to execute the next move in {@link #moves}, if it can't execute
     * that particular move, it keeps the move in the list and will try again
     * on the next iteration
     */
    public void executeNextMove(){
        if (this.moves.size() != 0) {
            Direction move = this.moves.get(0);
            if (move(move)) {
                this.moves.remove(0);
            }
        }
    }

    /**
     * moves the robot in one of the cardinal directions
     * @param direction {@link Inhabitant.Direction} (North, East, South, or West)
     * @return boolean - whether the move was able to be done
     */
    private synchronized boolean move(Inhabitant.Direction direction){
        int xOld = this.getLocation().getX();
        int yOld = this.getLocation().getY();
        int xNew, yNew;
        switch (direction){
            // Get the coordinates of the new location
            case WEST:
                xNew = xOld - 1;
                yNew = yOld;
                break;
            case SOUTH:
                xNew = xOld;
                yNew = yOld + 1;
                break;
            case EAST:
                xNew = xOld + 1;
                yNew = yOld;
                break;
            case NORTH:
                xNew = xOld;
                yNew = yOld - 1;
                break;
            default:
                xNew = -1;
                yNew = -1;
        }
        if (!this.getGrid().isValidLocation(xNew, yNew)) {
            // Location is out of bounds
            this.turnsSinceMove++;
            return false;
        }
        if (this.getGrid().getGridCell(xNew, yNew).getInhabitant().isPresent()){
            // The new location is already occupied
            this.turnsSinceMove++;
            return false;
        }
        setLocation(new Location(xNew, yNew));
        GridCell newGC = this.getGrid().getGridCell(xNew, yNew);
        if (!newGC.isOccupied()){
            this.getGrid().getGridCell(xOld, yOld).removeInhabitant();
            newGC.setInhabitant(this);
            this.unitsMoved++;
            if (getStatus() == Mode.PASSIVE_SEARCHING ||
                    getStatus() == Mode.ACTIVE_SEARCHING) {
                searchPerimeter();
            }
            this.hasMovedThisIteration = true;
            return true;
        } else {
            this.turnsSinceMove++;
            return false;
        }
    }

    /* END: Moving methods */

    /* START: Searching methods */

    /**
     * Searches the perimeter of the current robot. Searches GridCells
     * that are North, South, East, and West. If a {@link Target} is found
     * the {@link #targetFound(int, int)} method is called to tell the other
     * Robots that the target has been found
     */
    public void searchPerimeter(){
        int x, y;
        x = this.getLocation().getX();
        y = this.getLocation().getY();
        if (getGrid().isValidLocation(x, y - 1)) {
            // North
            if (searchGridCell(getGrid().getGridCell(x, y - 1))){
                // Target present
                targetFound(x, y - 1);
            }
        }
        if (getGrid().isValidLocation(x, y + 1)) {
            // South
            if (searchGridCell(getGrid().getGridCell(x, y + 1))){
                // Target present
                targetFound(x, y + 1);
            }
        }
        if (getGrid().isValidLocation(x - 1, y)) {
            // West
            if (searchGridCell(getGrid().getGridCell(x - 1, y))){
                // Target present
                targetFound(x - 1, y);
            }
        }
        if (getGrid().isValidLocation(x + 1, y)) {
            // East
            if (searchGridCell(getGrid().getGridCell(x + 1, y))){
                // Target present
                targetFound(x + 1, y);
            }
        }
    }

    /**
     * Switch from Mode.PASSIVE_SEARCHING to Mode.ACTIVE_SEARCHING and find
     * the closest non-searched cell and travel to it
     */
    private void activeSearching(){
        // Remove all future moves
        purgeMoves();

        // Set mode of Robot
        this.setStatus(Mode.ACTIVE_SEARCHING);

        // Set path to that of finding the closest non searched cell
        findUnsearchedCell();
    }

    /**
     * Setup for finding the closest unsearhed cell
     */
    private void findUnsearchedCell(){
        // Set path to that of finding the closest non searched cell
        Optional<Location> closestLocation = PathFinding.closestUnsearched(getLocation(), getGrid());
        closestLocation.ifPresent(
                location -> {
                    addMoves(PathFinding.generateMovesToSearch(location, getGrid()));
                    this.gridCellToSearch = getGrid().getGridCell(location);
                }

        );
    }

    /**
     * Search GridCell searches a given GridCell for the target
     * @param gridCell GridCell to search
     * @return true if the GridCell contains the {@link Target}
     */
    private boolean searchGridCell(GridCell gridCell){
        if (!gridCell.isSearched()){
            // Cell hasn't been searched before
            gridCell.setSearched(true);
            this.cellsSearchedThisIteration++;
            if (gridCell.isOccupied()){
                // Has something in the cell
                if (gridCell.getInhabitant().get() instanceof Target){
                    // Is the Target
                    return true;
                }
            }
        }
        return false;
    }

    /* END: Searching methods */

    /**
     * Getter for unitsMoved
     * @return int - number of units moved
     */
    public int getUnitsMoved() {
        return unitsMoved;
    }

    /* START: toString methods */

    /**
     * Created padding based on how long the robot name is
     * @param nameOfRobot the name of the robot
     * @return a String that is a number of spaces
     */
    private String getPadding(String nameOfRobot){
        String[] heros = RobotNames.ROBOT_NAMES;
        int longest = 0;
        for (int i = 0; i < heros.length; i++) {
            // Find the longest name
            if (heros[i].length() > longest)
                longest = heros[i].length();
        }
        int tabs = longest / 4;
        tabs++;
        int neededSize = (tabs * 4) + 1;
        int currentSize = nameOfRobot.length() + 2; // +2 for the single quotes
        int spacesNeeded = neededSize - currentSize;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * Like the toString method, just less info
     * @return 'Robot Name' @ (x, y)
     */
    public String info(){
        return "'" + this.name + "'" + getPadding(this.name) +
                "@ " + this.getLocation();
    }

    @Override
    public String toString() {
        return "BenignRobot '" + this.name + "':\n" +
                "\tLocation: " + this.getLocation() + "\n" +
                "\tUnits Moved: " + this.unitsMoved + "\n" +
                "\tStatus: " + this.getStatus();
    }

    /* END: toString methods */
}
