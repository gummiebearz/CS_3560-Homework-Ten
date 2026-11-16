public class User {
    private String name;
    private ChatHistory history;

    public User(String name) {
        this.name = name;
        this.history = new ChatHistory(this);
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
}
