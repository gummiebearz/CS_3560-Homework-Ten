import java.time.LocalDateTime;
import java.util.List;

public class Message {
    private User sender;
    private List<User> recipients;
    private LocalDateTime timestamp;
    private String content;

    public Message(User sender, List<User> recipients, LocalDateTime timestamp, String content) {
        this.sender = sender;
        this.recipients = recipients;
        this.timestamp = timestamp;
        this.content = content;
    }

    public User getSender() {
        return this.sender;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getContent() {
        return this.content;
    }

    public List<User> getRecipients() {
        return this.recipients;
    }
}
