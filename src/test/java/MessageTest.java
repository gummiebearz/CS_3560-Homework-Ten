import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class MessageTest {
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();
    private ChatServer server;

    @BeforeEach
    void setUp() {
        server = new ChatServer();
    }

    @Test
    void testGetSameSender() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);
        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");

        assertSame(sender, message.getSender());
    }

    @Test
    void testGetSameRecipients() {
        User sender = new User("Sender", server);
        User firstRecipient = new User("First Recipient", server);
        User secondRecipient = new User("Second Recipient", server);

        List<User> recipients = List.of(firstRecipient, secondRecipient);
        Message message = new Message(sender, recipients, TIMESTAMP, "Hello");

        assertSame(recipients, message.getRecipients());
    }

    @Test
    void testGetSameTimestamp() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        LocalDateTime now = TIMESTAMP;
        Message message = new Message(sender, List.of(recipient), now, "Hello");

        assertSame(now, message.getTimestamp());
    }

    @Test
    void testGetSameContent() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);
        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");

        assertSame("Hello", message.getContent());
    }
}
