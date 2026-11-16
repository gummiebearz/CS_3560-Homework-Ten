import java.time.LocalDateTime;
import java.util.List;

public class Message {
    private User sender;
    private List<User> recipients;
    private LocalDateTime timestamp;
    private String content;

    public Message(User sender, List<User> recipients, LocalDateTime timestamp, String content) {}

    public User getSender() {
        return null;
    }

    public LocalDateTime getTimestamp() {
        return null;
    }

    public String getContent() {
        return "";
    }

    public List<User> getRecipients() {
        return null;
    }
}
