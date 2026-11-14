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
        if(isChatHistoryEmpty()) {
            return null;
        }

        for(int i = this.messages.size() -1; i >= 0; --i) {
            Message message = this.messages.get(i);

            if(message.getSender().equals(this.owner)) {
                return message;
            }
        }

        return null;
    }
}
