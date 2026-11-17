import java.time.LocalDateTime;

public class MessageMemento implements Memento {
    private String content;
    private LocalDateTime timestamp;

    @Override
    public String getContent() {
        return "";
    }

    @Override
    public LocalDateTime getSnapshotDateTime() {
        return null;
    }
}
