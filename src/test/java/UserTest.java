import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class UserTest {
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

    @Test
    void testGetSameUserName() {
        String name = "Labrador";
        User sender = new User(name);

        assertEquals(name, sender.getName());
    }

    @Test
    void testGetUserChatHistory() {
        String name = "Labrador";
        User sender = new User(name);

        assertNotNull(sender.getChatHistory());
    }

    @Test
    void testMessageRetrievedFromChatHistory() {
        User sender = new User("Sender");
        User recipient = new User("Recipient");

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        sender.getChatHistory().addMessageToHistory(message);

        assertSame(message, sender.getChatHistory().getLastMessageSent());
    }

    @Test
    void testLastMessageSentBelongsToSender() {
        User sender = new User("Sender");
        User recipient = new User("Recipient");

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        sender.getChatHistory().addMessageToHistory(message);

        assertSame(sender, sender.getChatHistory().getLastMessageSent().getSender());
    }
}
