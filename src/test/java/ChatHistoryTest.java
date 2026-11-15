import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class ChatHistoryTest {
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

    @Test
    void testGetLastMessageSentOnOneMessage() {
        User sender = new User("Sender");
        User recipient = new User("Recipient");
        ChatHistory chatHistory = new ChatHistory(sender);

        Message firstMessage = new Message(sender, List.of(recipient), TIMESTAMP, "First Message");

        chatHistory.addMessageToHistory(firstMessage);

        assertSame(firstMessage, chatHistory.getLastMessageSent());
    }

    @Test
    void testGetLastMessageSentOnTwoMessages() {
        User sender = new User("Sender");
        User recipient = new User("Recipient");
        ChatHistory chatHistory = new ChatHistory(sender);

        Message firstMessage = new Message(sender, List.of(recipient), TIMESTAMP, "First Message");
        Message secondMessage = new Message(sender, List.of(recipient), TIMESTAMP, "Second Message");

        chatHistory.addMessageToHistory(firstMessage);
        chatHistory.addMessageToHistory(secondMessage);

        assertSame(secondMessage, chatHistory.getLastMessageSent());
    }

    @Test
    void testGetLastMessageSentReturnsNullWhenOnlyReceivedMessages() {
        User sender = new User("Sender");
        User recipient = new User("Recipient");
        ChatHistory chatHistory = new ChatHistory(sender);

        Message receivedMessage = new Message(recipient, List.of(sender), TIMESTAMP, "Received Message");
        chatHistory.addMessageToHistory(receivedMessage);

        assertNull(chatHistory.getLastMessageSent());
    }

    @Test
    void testGetLastMessageSentReturnsNullWhenHistoryIsEmpty() {
        User sender = new User("Sender");
        ChatHistory chatHistory = new ChatHistory(sender);

        assertNull(chatHistory.getLastMessageSent());
    }
}
