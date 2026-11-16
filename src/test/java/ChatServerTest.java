import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class ChatServerTest {
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();
    private ChatServer server;

    @BeforeEach
    void setUp() {
        server = new ChatServer();
    }

    @Test
    void testNewServerHasNoRegisteredUsers() {
        assertTrue(server.getRegisteredUsers().isEmpty());
    }

    @Test
    void testRegisterUser() {
        User user = new User("New User", server);

        server.registerUser(user);

        assertTrue(server.getRegisteredUsers().contains(user));
    }

    @Test
    void testUnregisterUser() {
        User user = new User("New User", server);

        server.registerUser(user);
        server.unregisterUser(user);

        assertFalse(server.getRegisteredUsers().contains(user));
    }

    @Test
    void testSendMessageDeliveredToRecipient() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        server.registerUser(sender);
        server.registerUser(recipient);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");

        server.sendMessage(message);

        assertEquals(List.of(message), recipient.getChatHistory().getMessages());
    }

    @Test
    void testSendMessageDeliveredToMultipleRecipients() {
        User sender = new User("Sender", server);
        User firstRecipient = new User("First Recipient", server);
        User secondRecipient = new User("Second Recipient", server);

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
        User sender = new User("Sender", server);
        User firstRecipient = new User("First Recipient", server);
        User secondRecipient = new User("Second Recipient", server);

        server.registerUser(sender);
        server.registerUser(firstRecipient);

        Message message = new Message(sender, List.of(firstRecipient, secondRecipient), TIMESTAMP, "Hello");

        server.sendMessage(message);

        assertEquals(List.of(message), firstRecipient.getChatHistory().getMessages());
        assertTrue(secondRecipient.getChatHistory().getMessages().isEmpty());
    }

    @Test
    void testSendMessageDoesNotDeliverToUnlistedUsers() {
        User sender = new User("Sender", server);
        User firstRecipient = new User("First Recipient", server);
        User secondRecipient = new User("Second Recipient", server);

        server.registerUser(sender);
        server.registerUser(firstRecipient);
        server.registerUser(secondRecipient);

        Message message = new Message(sender, List.of(firstRecipient), TIMESTAMP, "Hello");

        server.sendMessage(message);

        assertEquals(List.of(message), firstRecipient.getChatHistory().getMessages());
        assertTrue(secondRecipient.getChatHistory().getMessages().isEmpty());
    }
}
