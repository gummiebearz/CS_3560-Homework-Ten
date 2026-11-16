import java.time.LocalDateTime;

public interface Memento {
    public String getContent();
    public LocalDateTime getSnapshotDateTime();
}
