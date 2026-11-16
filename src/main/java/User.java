import java.util.Iterator;
import java.util.List;
import java.time.LocalDateTime;

public class User implements IterableByUser {
    private String name;
    private ChatHistory history;
    private ChatMediator mediator;

    // Declared as static because the memento just holds copied values
    private static class MessageMemento implements Memento {
        private String content;
        private LocalDateTime timestamp;

        public MessageMemento(String content, LocalDateTime timestamp) {
            this.content = content;
            this.timestamp = timestamp;
        }

        @Override
        public String getContent() {
            return this.content;
        }

        @Override
        public LocalDateTime getSnapshotDateTime() {
            return this.timestamp;
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
        this.history.storeSnapshot(saveSnapshot());
        this.mediator.sendMessage(messageToSend);
    }

    public void undoLastMessageSent() {
        Memento snapshot = this.history.popLastSnapshot();

        if(snapshot != null) {
            undoSnapshot(snapshot);
        }
    }

    public Memento saveSnapshot() {
        Message lastMessageSent = this.history.getLastMessageSent();
        if(lastMessageSent == null) {
            return null;
        }

        return new MessageMemento(lastMessageSent.getContent(), lastMessageSent.getTimestamp());
    }

    public void undoSnapshot(Memento snapshot) {
        MessageMemento memento = (MessageMemento) snapshot;

        for(Message message : this.history.getMessages()) {
            if(message.getSender().equals(this)
            && message.getContent().equals(memento.content)
            && message.getTimestamp().equals(memento.timestamp)) {
                this.history.removeMessageFromHistory(message);
                return;
            }
        }
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return this.history.iterator(userToSearchWith);
    }
}
