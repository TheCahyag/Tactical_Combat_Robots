package message;

import inhabitant.Inhabitant;

/**
 * File: TargetFoundMessage.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class TargetFoundMessage extends Message {

    public TargetFoundMessage(Inhabitant sender, Inhabitant receiver) {
        super(sender, receiver);
    }

    @Override
    public void parseMessage() {

    }

    @Override
    public void sendMessage() {

    }
}
