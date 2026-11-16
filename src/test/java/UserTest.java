import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class UserTest {
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();
    private ChatServer server;

    @BeforeEach
    void setUp() {
        server = new ChatServer();
    }

    @Test
    void testGetSameUserName() {
        String name = "Labrador";
        User sender = new User(name, server);

        assertEquals(name, sender.getName());
    }

    @Test
    void testGetUserChatHistory() {
        String name = "Labrador";
        User sender = new User(name, server);

        assertNotNull(sender.getChatHistory());
    }

    @Test
    void testMessageRetrievedFromChatHistory() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        sender.getChatHistory().addMessageToHistory(message);

        assertSame(message, sender.getChatHistory().getLastMessageSent());
    }

    @Test
    void testLastMessageSentBelongsToSender() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        sender.getChatHistory().addMessageToHistory(message);

        assertSame(sender, sender.getChatHistory().getLastMessageSent().getSender());
    }

    @Test
    void testReceivedMessageAddedToChatHistory() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);
        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");

        recipient.receiveMessage(message);
        assertEquals(List.of(message), recipient.getChatHistory().getMessages());
    }

    @Test
    void testReceivedMessageNotCountedAsSent() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);
        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");

        recipient.receiveMessage(message);
        assertNull(recipient.getChatHistory().getLastMessageSent());
    }

    @Test
    void testSendMessageAddedToSenderChatHistory() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        String content = "Hello";
        sender.sendMessage(List.of(recipient), content);

        assertNotNull(sender.getChatHistory().getLastMessageSent());
    }

    @Test
    void testSentMessageHasCorrectDetails() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        String content = "Hello";
        sender.sendMessage(List.of(recipient), content);
        Message sentMessage = sender.getChatHistory().getLastMessageSent();

        assertSame(sender, sentMessage.getSender());
        assertEquals(List.of(recipient), sentMessage.getRecipients());
        assertEquals(content, sentMessage.getContent());
        assertNotNull(sentMessage.getTimestamp());
    }

    @Test
    void testSendMessageDeliveredToRecipientThroughMediator() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        server.registerUser(sender);
        server.registerUser(recipient);

        sender.sendMessage(List.of(recipient), "Hello");

        assertEquals(1, recipient.getChatHistory().getMessages().size());
        assertEquals("Hello", recipient.getChatHistory().getMessages().getLast().getContent());
    }

    @Test
    void testSendMessageDoesNotDuplicateInSenderHistory() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        server.registerUser(sender);
        server.registerUser(recipient);

        sender.sendMessage(List.of(recipient), "Hello");

        assertEquals(1, sender.getChatHistory().getMessages().size());
    }

    @Test
    void testBlockUsers() {
        User firstUser = new User("First User", server);
        User secondUser = new User("Second User", server);
        User thirdUser = new User("Third User", server);

        server.registerUser(firstUser);
        server.registerUser(secondUser);
        server.registerUser(thirdUser);

        server.blockUsers(firstUser, List.of(secondUser, thirdUser));

        assertTrue(firstUser.getChatHistory().getMessages().isEmpty());
    }

    @Test
    void testBlockedSenderMessageNotDelivered() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        server.registerUser(sender);
        server.registerUser(recipient);

        server.blockUsers(recipient, List.of(sender));
        server.sendMessage(new Message(sender, List.of(recipient), TIMESTAMP, "Hello"));

        assertTrue(recipient.getChatHistory().getMessages().isEmpty());
    }

    @Test
    void testBlockerCanStillSendMessageToBlockedUsers() {
        User blocker = new User("Blocker", server);
        User blocked = new User("Blocked", server);

        server.registerUser(blocker);
        server.registerUser(blocked);

        server.blockUsers(blocker, List.of(blocked));
        Message message = new Message(blocker, List.of(blocked), TIMESTAMP, "Hello");
        server.sendMessage(message);

        assertEquals(List.of(message), blocked.getChatHistory().getMessages());
    }
}
