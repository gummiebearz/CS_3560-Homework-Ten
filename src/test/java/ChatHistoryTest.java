import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class ChatHistoryTest {
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

    @Test
    void testAddMessageToHistory() {
        User sender = new User("Sender");
        User recipient = new User("Recipient");
        ChatHistory chatHistory = new ChatHistory(sender);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        chatHistory.addMessageToHistory(message);

        assertSame(message, chatHistory.getLastMessageSent());
    }

    @Test
    void testGetLastMessageSentFromHistory() {
        User sender = new User("Sender");
        User recipient = new User("Recipient");
        ChatHistory chatHistory = new ChatHistory(sender);

        Message firstMessage = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        Message secondMessage = new Message(sender, List.of(recipient), TIMESTAMP, "Hello again");
        chatHistory.addMessageToHistory(firstMessage);
        chatHistory.addMessageToHistory(secondMessage);

        assertSame(secondMessage, chatHistory.getLastMessageSent());
    }
}
