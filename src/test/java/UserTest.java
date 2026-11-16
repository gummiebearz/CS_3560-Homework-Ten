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
    void testUndoRemovesLastMessageSent() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        server.registerUser(sender);
        server.registerUser(recipient);

        sender.sendMessage(List.of(recipient), "Hello");
        sender.undoLastMessageSent();

        assertNull(sender.getChatHistory().getLastMessageSent());
    }

    @Test
    void testUndoRestoresPreviousSentMessage() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        server.registerUser(sender);
        server.registerUser(recipient);

        sender.sendMessage(List.of(recipient), "First Hello");
        sender.sendMessage(List.of(recipient), "Second Hello");
        sender.undoLastMessageSent();

        assertEquals("First Hello", sender.getChatHistory().getLastMessageSent().getContent());
    }

    @Test
    void testUndoDoesNotAffectRecipientHistory() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        server.registerUser(sender);
        server.registerUser(recipient);

        sender.sendMessage(List.of(recipient), "Hello");
        sender.undoLastMessageSent();

        assertEquals(1, recipient.getChatHistory().getMessages().size());
    }

    @Test
    void testUndoWithNothingSentDoesNothing() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        server.registerUser(sender);
        server.registerUser(recipient);

        assertDoesNotThrow(sender::undoLastMessageSent);
        assertNull(sender.getChatHistory().getLastMessageSent());
    }
}
