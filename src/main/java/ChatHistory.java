import java.util.ArrayList;
import java.util.List;

public class ChatHistory {
    private List<Message> messages;
    private User owner;

    public ChatHistory(User owner) {
        this.messages = new ArrayList<>();
        this.owner = owner;
    }

    private boolean isChatHistoryEmpty() {
        return this.messages.isEmpty();
    }

    public void addMessageToHistory(Message message) {
        this.messages.add(message);
    }

    public Message getLastMessageSent() {
        return this.messages.isEmpty() ? null : this.messages.get(this.messages.size() - 1);
    }
}
