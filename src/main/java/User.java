import java.util.List;

public class User {
    private String name;
    private ChatHistory chatHistory;
    private ChatMediator chatMediator;

    public User(String name, ChatMediator chatMediator) {
        this.name = name;
        this.chatHistory = new ChatHistory(this);
        this.chatMediator = chatMediator;
    }

    public String getName() {
        return this.name;
    }

    public ChatHistory getChatHistory() {
        return this.chatHistory;
    }

    // Added stub methods
    public void sendMessage(List<User> recipients, Message messageToSend) {
        this.chatHistory.addMessageToHistory(messageToSend);
    }
    public void receiveMessage(Message messageToReceive) {}
}
