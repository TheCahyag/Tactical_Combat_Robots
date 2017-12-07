package message;

import inhabitant.Inhabitant;

/**
 * File: Message.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public abstract class Message {
    Inhabitant receiver;
    Inhabitant sender;

    public Message(Inhabitant sender, Inhabitant receiver){
        this.receiver = receiver;
        this.sender = sender;
    }

    public abstract Object parseMessage();

    public abstract void sendMessage();
}
