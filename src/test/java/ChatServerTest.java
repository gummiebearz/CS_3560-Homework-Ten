import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class ChatServerTest {
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

    @Test
    void testNewServerHasNoRegisteredUsers() {
        ChatServer server = new ChatServer();
        assertTrue(server.getRegisteredUsers().isEmpty());
    }

    @Test
    void testRegisterUser() {
        ChatServer server = new ChatServer();
        User user = new User("New User");

        server.registerUser(user);

        assertTrue(server.getRegisteredUsers().contains(user));
    }

    @Test
    void testUnregisterUser() {
        ChatServer server = new ChatServer();
        User user = new User("New User");

        server.registerUser(user);
        server.unregisterUser(user);

        assertFalse(server.getRegisteredUsers().contains(user));
    }

    @Test
    void testSendMessageDeliveredToRecipient() {
        ChatServer server = new ChatServer();
        User sender = new User("Sender");
        User recipient = new User("Recipient");

        server.registerUser(sender);
        server.registerUser(recipient);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");

        server.sendMessage(message);

        assertEquals(List.of(message), recipient.getChatHistory().getMessages());
    }

    @Test
    void testSendMessageDeliveredToMultipleRecipients() {
        ChatServer server = new ChatServer();
        User sender = new User("Sender");
        User firstRecipient = new User("First Recipient");
        User secondRecipient = new User("Second Recipient");

        server.registerUser(sender);
        server.registerUser(firstRecipient);
        server.registerUser(secondRecipient);

        Message message = new Message(sender, List.of(firstRecipient, secondRecipient), TIMESTAMP, "Hello");

        server.sendMessage(message);

        assertEquals(List.of(message), firstRecipient.getChatHistory().getMessages());
        assertEquals(List.of(message), secondRecipient.getChatHistory().getMessages());
    }

    @Test
    void testSendMessageDeliveredToRegisteredUsersOnly() {
        ChatServer server = new ChatServer();
        User sender = new User("Sender");
        User firstRecipient = new User("First Recipient");
        User secondRecipient = new User("Second Recipient");

        server.registerUser(sender);
        server.registerUser(firstRecipient);

        Message message = new Message(sender, List.of(firstRecipient, secondRecipient), TIMESTAMP, "Hello");

        server.sendMessage(message);

        assertEquals(List.of(message), firstRecipient.getChatHistory().getMessages());
        assertTrue(secondRecipient.getChatHistory().getMessages().isEmpty());
    }

    @Test
    void testSendMessageDoesNotDeliverToUnlistedUsers() {
        ChatServer server = new ChatServer();
        User sender = new User("Sender");
        User firstRecipient = new User("First Recipient");
        User secondRecipient = new User("Second Recipient");

        server.registerUser(sender);
        server.registerUser(firstRecipient);
        server.registerUser(secondRecipient);

        Message message = new Message(sender, List.of(firstRecipient), TIMESTAMP, "Hello");

        server.sendMessage(message);

        assertEquals(List.of(message), firstRecipient.getChatHistory().getMessages());
        assertTrue(secondRecipient.getChatHistory().getMessages().isEmpty());
    }
}
