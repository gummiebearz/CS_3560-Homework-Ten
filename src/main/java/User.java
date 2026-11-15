public class User {
    private String name;
    private ChatHistory chatHistory;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ChatHistory getChatHistory() {
        return null;
    }
}
