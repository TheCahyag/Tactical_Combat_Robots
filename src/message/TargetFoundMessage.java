package message;

import grid.Location;
import inhabitant.Inhabitant;

/**
 * File: TargetFoundMessage.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class TargetFoundMessage extends Message {

    private Location target;

    public TargetFoundMessage(Inhabitant sender, Inhabitant receiver, Location location) {
        super(sender, receiver);
        this.target = location;
    }

    @Override
    public Location parseMessage() {
        return this.target;
    }

    @Override
    public void sendMessage() {
        this.receiver.receiveMessage(this);
    }
}
