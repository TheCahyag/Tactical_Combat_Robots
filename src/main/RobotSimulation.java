package main;

import grid.Grid;
import grid.PathFinding;
import inhabitant.BenignRobot;
import inhabitant.Inhabitant;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * File: main.RobotSimulation.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class RobotSimulation {
    private static Grid grid;
    private static int xLen;
    private static int yLen;
    private static int robots;
    private static boolean isCommandLine = true;
    private static long delay = 1000;
    public static volatile boolean running = true;
    private static List<Thread> robotThreads;

    public static void main(String[] args) {
        if (args.length == 0){
            printUsage();
            return;
        }
        try {
            // Parse X argument (horizontal size of grid)
            xLen = Integer.parseInt(args[0]);
            // Parse Y argument (vertical size of grid)
            yLen = Integer.parseInt(args[1]);
            // Parse N argument (number of robots)
            robots = Integer.parseInt(args[2]);
        } catch (NumberFormatException e){
            System.err.println("Failed to parse argument: not an integer.");
            return;
        }

        // Verify robot argument
        if (args.length > 2) {
            if (args[3].charAt(1) == 'a') {
                if (args[3].length() > 2) {
                    String argNum = args[1].substring(2, args[1].length());
                    try {
                        delay = Long.parseLong(argNum);
                    } catch (NumberFormatException e) {
                        System.err.println("Failed to parse 'D' argument: not an integer. " +
                                "Defaulting to 1 second.");
                    }
                }
                isCommandLine = false;
            }
        }
        runSimulation();
    }

    private static void runSimulation(){
        // Make/init grid
        grid = new Grid(xLen, yLen);
        grid.init(robots);

        List<BenignRobot> robots = grid.getRobots();
        for (int i = 0; i < robots.size(); i++) {
            BenignRobot robot = robots.get(i);
            Inhabitant.Direction[] directions = PathFinding.generateMovesToSearch(robot.getLocation(), grid);
            robot.addMoves(directions);
            robot.searchPerimeter();
        }
        robotThreads = new ArrayList<>();
        for (int i = 0; i < robots.size(); i++) {
            robotThreads.add(new Thread(robots.get(i)));
        }
        for (int i = 0; i < robotThreads.size(); i++) {
            robotThreads.get(i).start();
        }

        // Pass off to command line or automatic
        if (isCommandLine){
            // Start the command line
            commandLineSimulation();
        } else {
            // Start the simulation
            automaticSimulation(delay);
        }
    }

    private static void commandLineSimulation(){
        Scanner in = new Scanner(System.in);
        do {
            System.out.flush();
            System.out.print("> ");
            if (in.hasNextLine()) {
                String cmd = in.nextLine();
                action(cmd);
            } else {
                System.out.println();
                isCommandLine = false;
            }
        } while (isCommandLine);
    }

    private static void action(String command){
        String[] args = command.toLowerCase().split(" ");
        switch (args[0]){
            case "step": // Iterate threw the simulation N times
                if (!running){
                    System.err.println("The simulation is not/done running.");
                    break;
                }
                int steps = 0;
                if (args.length > 2){
                    // To many arguments
                    System.err.println("Usage: step [N] (Where N is an integer)");
                    break;
                }
                if (args.length == 1){
                    // N wasn't specified
                    steps = 1;
                } else {
                    // N was specified
                    try {
                        steps = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        System.err.println("Failed to parse N argument: Not an integer.");
                    }
                }
                for (int i = 0; i < steps; i++) {
                    step();
                }
                break;
            case "run":
                if (!running){
                    System.err.println("The simulation is done running.");
                    break;
                }
                long delay = 1000;
                if (args.length > 1){
                    try {
                        delay = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e){
                        System.err.println("Failed to parse N argument: Not an integer.");
                        break;
                    }
                }
                isCommandLine = false;
                automaticSimulation(delay);
                break;
            case "grid":
                System.out.println(grid);
                break;
            case "target": // Print information about the target
                System.out.println(grid.getTarget());
                break;
            case "robots": // Print all Benign Robots
                ArrayList<BenignRobot> robotList = grid.getRobots();
                for (int i = 0; i < robotList.size(); i++) {
                    System.out.println(i + ") " + robotList.get(i).info());
                }
                break;
            case "robot": // Robot N command
                if (args.length != 2){
                    System.err.println("Usage: robot N (Where N is an integer)");
                    break;
                }
                int robotIndex;
                try {
                    robotIndex = Integer.parseInt(args[1]);
                } catch (NumberFormatException e){
                    System.err.println("Failed to parse N argument: Not an integer.");
                    break;
                }
                ArrayList<BenignRobot> theRobots = grid.getRobots();
                if (theRobots.size() <= robotIndex){
                    System.err.println("Number provided was invalid. Type 'robots' for indexing");
                    break;
                }
                System.out.println(theRobots.get(robotIndex));
                break;
            case "help": // Print help message
                System.out.println("step [N]\t# Step forward N iterations, or one by default");
                System.out.println("run [N]\t\t# Automatically run the simulation with a delay of N milliseconds between each iteration, or 1000 by default");
                System.out.println("grid\t\t# Display the grid");
                System.out.println("target\t\t# Display information about the target");
                System.out.println("robots\t\t# Display the list of robots that are currently on the grid");
                System.out.println("robot N\t\t# Display information about the N'th robot (type 'robots' for indexing)");
                System.out.println("help\t\t# View this message");
                System.out.println("info\t\t# View statistics regarding the current simulation");
                System.out.println("exit\t\t# Exit the simulation");
                break;
            case "info":
                System.out.println(grid.getStatistics());
                break;
            case "exit": // Exit program
                System.out.println("Goodbye.");
                isCommandLine = false;
                break;
            case "": // Empty line
                break;
            default:
                System.err.println("Unrecognized Command: " + command + ". Type 'help' for a list of commands.");
                break;
        }
    }

    /**
     * TODO
     * @param sleepTime
     */
    private static void automaticSimulation(long sleepTime){

        do {
            step();
            System.out.println(grid);
            System.out.println();
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (running);
        isCommandLine = true;
        commandLineSimulation();
    }

    private static void step(){
        synchronized (grid) {
            grid.notifyAll();
            grid.incrementIteration();
            ArrayList<BenignRobot> robots = grid.getRobots();
            for (int i = 0; i < robots.size(); i++) {
                // If all robots are in position the simulation is over
                if (robots.get(i).getStatus() != Inhabitant.Mode.IN_POSITION)
                    return;
            }
            System.out.println("All robots are in position!");
            running = false;
        }
    }

    private static void printUsage(){
        System.out.println("Usage: RobotSimulation XDIM YDIM R [-a[D]|-c]");
    }
}
