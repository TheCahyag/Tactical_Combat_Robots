package message;

/**
 * File: Messageable.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public interface Messageable {

    /**
     * Send a object a message
     * @param message given {@link Message}
     */
    void receiveMessage(Message message);
}
