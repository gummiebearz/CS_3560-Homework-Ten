import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatHistory implements IterableByUser {
    private User owner;
    private List<Message> history;
    private List<Memento> snapshots;

    public ChatHistory(User owner) {
        this.owner = owner;
        this.history = new ArrayList<>();
        this.snapshots = new ArrayList<>();
    }

    public void addMessageToHistory(Message message) {
        this.history.add(message);
    }

    public Message getLastMessageSent() {
        if(history.isEmpty())
            return null;

        for(int i = this.history.size() - 1; i >= 0; --i) {
            if(this.history.get(i).getSender().equals(owner)) {
                return this.history.get(i);
            }
        }

        return null;
    }

    public List<Message> getMessages() {
        return this.history;
    }

    public void storeSnapshot(Memento snapshot) {
        this.snapshots.add(snapshot);
    }

    public Memento popLastSnapshot() {
        if(this.snapshots.isEmpty())
            return null;

        return this.snapshots.removeLast();
    }


    public void removeMessageFromHistory(Message message) {
        this.history.remove(message);
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return null;
    }
}
