import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void testGetSameUserName() {
        String name = "Labrador";
        User sender = new User(name);

        assertEquals(name, sender.getName());
    }

    @Test
    void testGetUserChatHistory() {
        String name = "Labrador";
        User sender = new User(name);

        assertNotNull(sender.getChatHistory());
    }
}
