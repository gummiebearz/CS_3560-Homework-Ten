import java.util.List;
import java.time.LocalDateTime;

public class User {
    private String name;
    private ChatHistory history;
    private ChatMediator mediator;

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
}
