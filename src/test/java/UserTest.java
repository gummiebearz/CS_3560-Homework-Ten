import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class UserTest {
    private static final String NAME = "John Doe";
    @Test
    void testGetUsername() {
        User user = new User(NAME);

        assertEquals(NAME, user.getName());
    }
}
