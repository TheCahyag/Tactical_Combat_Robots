package message;

import grid.Location;
import inhabitant.Inhabitant;

/**
 * File: SurroundingTargetLocationMessage.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SurroundingTargetLocationMessage extends Message {

    private Location location;
    private int distance;

    public SurroundingTargetLocationMessage(Inhabitant sender, Inhabitant receiver, Location location, int distance){
        super(sender, receiver);
        this.location = location;
        this.distance = distance;
    }

    @Override
    public Location parseMessage() {
        return this.location;
    }

    @Override
    public void sendMessage() {
        this.receiver.receiveMessage(this);
    }

    public int getDistance(){
        return this.distance;
    }
}
