package message;

import inhabitant.Inhabitant;

/**
 * File: Message.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public abstract class Message {
    private Inhabitant receiver;
    private Inhabitant sender;

    public Message(Inhabitant sender, Inhabitant receiver){

    }

    public abstract Object parseMessage();

    public abstract void sendMessage();
}
