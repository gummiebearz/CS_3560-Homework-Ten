import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatServerTest {
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();
    private final ChatServer server = new ChatServer();

    private static class RecordingUser extends User {
        private final List<Message> receivedMessages = new ArrayList<>();

        RecordingUser(String name, ChatMediator mediator) {
            super(name, mediator);
        }

        @Override
        public void receiveMessage(Message messageToReceive) {
            this.receivedMessages.add(messageToReceive);
        }

        List<Message> getReceivedMessages() {
            return this.receivedMessages;
        }
    }

    @Test
    void testSendMessageDeliversToRegisteredRecipient() {
        User sender = new User("Sender", server);
        RecordingUser recipient = new RecordingUser("Recipient", server);
        server.registerUser(sender);
        server.registerUser(recipient);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        server.sendMessage(message);

        assertEquals(List.of(message), recipient.getReceivedMessages());
    }

    @Test
    void testSendMessageDeliversToMultipleRecipients() {
        User sender = new User("Sender", server);
        RecordingUser firstRecipient = new RecordingUser("First Recipient", server);
        RecordingUser secondRecipient = new RecordingUser("Second Recipient", server);
        server.registerUser(sender);
        server.registerUser(firstRecipient);
        server.registerUser(secondRecipient);

        Message message = new Message(sender, List.of(firstRecipient, secondRecipient), TIMESTAMP, "Hello");
        server.sendMessage(message);

        assertEquals(List.of(message), firstRecipient.getReceivedMessages());
        assertEquals(List.of(message), secondRecipient.getReceivedMessages());
    }

    @Test
    void testSendMessageSkipsUnregisteredRecipient() {
        User sender = new User("Sender", server);
        RecordingUser recipient = new RecordingUser("Recipient", server);
        server.registerUser(sender);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        server.sendMessage(message);

        assertTrue(recipient.getReceivedMessages().isEmpty());
    }

    @Test
    void testSendMessageDoesNotDeliverToNonRecipients() {
        User sender = new User("Sender", server);
        RecordingUser recipient = new RecordingUser("Recipient", server);
        RecordingUser nonRecipient = new RecordingUser("Non Recipient", server);
        server.registerUser(sender);
        server.registerUser(recipient);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        server.sendMessage(message);

        assertTrue(nonRecipient.getReceivedMessages().isEmpty());
    }
}
