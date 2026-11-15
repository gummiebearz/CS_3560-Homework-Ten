import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class MessageTest {
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();
    private static final String MSG_CONTENT = "Hello";

    // Added reference to mediator
    private final ChatMediator mediator = new ChatServer();

    @Test
    void testGetMessageSender() {
        User sender = new User("Sender", mediator);
        User recipient = new User("Recipient", mediator);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, MSG_CONTENT);

        assertSame(sender, message.getSender());
    }

    @Test
    void testGetSingleRecipient() {
        User sender = new User("Sender", mediator);
        User recipient = new User("Recipient", mediator);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, MSG_CONTENT);

        assertEquals(List.of(recipient), message.getRecipients());
    }

    @Test
    void testGetMultipleRecipients() {
        User sender = new User("Sender", mediator);
        User firstRecipient = new User("First Recipient", mediator);
        User secondRecipient = new User("Second Recipient", mediator);

        List<User> recipients = List.of(firstRecipient, secondRecipient);

        Message message = new Message(sender, recipients, TIMESTAMP, MSG_CONTENT);

        assertEquals(recipients, message.getRecipients());
    }

    @Test
    void testGetTimestamp() {
        User sender = new User("Sender", mediator);
        User recipient = new User("Recipient", mediator);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, MSG_CONTENT);

        assertEquals(TIMESTAMP, message.getTimestamp());
    }

    @Test
    void testGetMessageContent() {
        User sender = new User("Sender", mediator);
        User recipient = new User("Recipient", mediator);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, MSG_CONTENT);

        assertEquals(MSG_CONTENT, message.getContent());
    }
}
