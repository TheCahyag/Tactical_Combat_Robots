# Tactical Robot Combat
Project 3/4 - CSCI 251

### Needs:
* Java 8 JRE

### Running the program
**Navigating to the directory to run the program TODO **  
NAME  
    RobotSimulation  
  
SYNOPSIS  
    RobotSimulation XDIM YDIM R \[-a\[D]|-c]  
  
DESCRIPTION  
    XDIM - x dimension for the grid
    YDIM - y dimension for the grid
    R - number of robots to spawn on the grid
    -a\[D] - specifies that the simulation will automatically run with a delay of D milliseconds or 1000 by default
    -c - specifies that the simulation will start on the command line

### Example executions
* `RobotSimulation 15 15 10 -c`  
Run a 15x15 grid with 10 robots in our simulation and start it on the command line  
* `RobotSimulation 15 15 10 -a1000`  
Run a 15x15 grid with 10 robots in our simulation and run it automatically with a 1000 millisecond delay time  
* `RobotSimulation 15 15 10`
Run a 15x15 grid with 10 robots in our simulation and start it on the command line
