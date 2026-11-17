import java.time.LocalDateTime;

public interface Memento {
    String getContent();
    LocalDateTime getSnapshotDateTime();
}
