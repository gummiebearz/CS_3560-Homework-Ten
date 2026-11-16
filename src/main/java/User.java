import java.util.List;
import java.time.LocalDateTime;

public class User {
    private String name;
    private ChatHistory history;
    private ChatMediator mediator;

    // Declared as static because the memento just holds copied values
    private static class MessageMemento implements Memento {
        private String content;
        private LocalDateTime timestamp;

        public MessageMemento(String content, LocalDateTime timestamp) {}

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public LocalDateTime getSnapshotDateTime() {
            return null;
        }
    }

    public User(String name, ChatMediator mediator) {
        this.name = name;
        this.history = new ChatHistory(this);
        this.mediator = mediator;
    }

    public String getName() {
        return this.name;
    }

    public ChatHistory getChatHistory() {
        return this.history;
    }

    public void receiveMessage(Message messageToReceive) {
        this.history.addMessageToHistory(messageToReceive);
    }

    public void sendMessage(List<User> recipients, String content) {
        Message messageToSend = new Message(this, recipients, LocalDateTime.now(), content);
        this.history.addMessageToHistory(messageToSend);
        this.mediator.sendMessage(messageToSend);
    }

    public void undoLastMessageSent() {}

    public Memento saveSnapshot() {
        return null;
    }

    public void restoreSnapshot(Memento snapshot) {}
}
