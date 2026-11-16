import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatHistoryTest {
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();
    private ChatServer server;

    @BeforeEach
    public void setUp() {
        server = new ChatServer();
    }

    @Test
    void testAddMessageToHistory() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);
        ChatHistory chatHistory = new ChatHistory(sender);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        chatHistory.addMessageToHistory(message);

        assertSame(message, chatHistory.getLastMessageSent());
    }

    @Test
    void testGetLastMessageSentFromHistory() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);
        ChatHistory chatHistory = new ChatHistory(sender);

        Message firstMessage = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        Message secondMessage = new Message(sender, List.of(recipient), TIMESTAMP, "Hello again");
        chatHistory.addMessageToHistory(firstMessage);
        chatHistory.addMessageToHistory(secondMessage);

        assertSame(secondMessage, chatHistory.getLastMessageSent());
    }

    @Test
    void testPopLastSnapshotReturnsNewest() {
        User owner =  new User("Owner", server);
        ChatHistory history = owner.getChatHistory();

        owner.sendMessage(List.of(), "First Message");
        owner.sendMessage(List.of(), "Second Message");

        assertEquals("Second Message", history.popLastSnapshot().getContent());
        assertEquals("First Message", history.popLastSnapshot().getContent());
    }

    @Test
    void testPopLastSnapshotOnEmptyReturnsNull() {
        ChatHistory history = new ChatHistory(new User("Owner", server));

        assertNull(history.popLastSnapshot());
    }

    @Test
    void testRemoveMessageFromHistory() {
        User owner = new User("Owner", server);
        ChatHistory history = new ChatHistory(owner);
        Message message = new Message(owner, List.of(), TIMESTAMP, "Hello");

        history.removeMessageFromHistory(message);
        assertTrue(history.getMessages().isEmpty());
    }

    @Test
    void testIteratorOnEmptyHistoryHasNoNext() {
        User user = new User("User", server);

        assertFalse(user.getChatHistory().iterator(user).hasNext());
    }

    @Test
    void testIteratorYieldsMessageSentByUser() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        Message message = new Message(sender, List.of(recipient), TIMESTAMP, "Hello");
        sender.getChatHistory().addMessageToHistory(message);

        Iterator<Message> iterator = sender.getChatHistory().iterator(sender);

        assertSame(message, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorKeepsMessageOrder() {
        User sender = new User("Sender", server);
        User recipient = new User("Recipient", server);

        Message firstMessage = new Message(sender, List.of(recipient), TIMESTAMP, "First Message");
        Message secondMessage = new Message(recipient, List.of(sender), TIMESTAMP, "Second Message");

        sender.getChatHistory().addMessageToHistory(firstMessage);
        sender.getChatHistory().addMessageToHistory(secondMessage);

        Iterator<Message> iterator = sender.getChatHistory().iterator(sender);

        assertSame(firstMessage, iterator.next());
        assertSame(secondMessage, iterator.next());
        assertFalse(iterator.hasNext());
    }
}
