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
    private static int robots;
    private static boolean isCommandLine = true;
    private static long delay = 1000;

    public static void main(String[] args) {
        try {
            // Parse N argument (number of robots)
            robots = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            System.err.println("Failed to parse 'N' argument: not an integer.");
            return;
        }
        if (args[1].charAt(1) == 'a'){
            if (args[1].length() > 2){
                String argNum = args[1].substring(2, args[1].length());
                try {
                    delay = 1000 * Long.parseLong(argNum);
                } catch (NumberFormatException e){
                    System.err.println("Failed to parse 'D' argument: not an integer. " +
                            "Defaulting to 1 second.");
                }
            }
            isCommandLine = false;
        }
        runSimulation();








//        Grid grid = new Grid(10);
//        RobotSimulation.grid = grid;
//        grid.init(3);
//        System.out.println(grid);
//        for (int i = 0; i < 5; i++) {
//            //((BenignRobot) grid.inhabitants.get(1)).addMove(Inhabitant.Direction.NORTH);
//            System.out.println(grid);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private static void runSimulation(){
        // Make/init grid

        // pass off to command line or automatic
    }

    private static void commandLineSimulation(){

    }

    private static void automaticSimulation(long sleepTime){

    }
}
