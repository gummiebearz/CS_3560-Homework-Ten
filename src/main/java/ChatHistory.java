import java.util.ArrayList;
import java.util.List;

public class ChatHistory {
    private User owner;
    private List<Message> history;

    public ChatHistory(User owner) {
        this.owner = owner;
        this.history = new ArrayList<>();
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
}
