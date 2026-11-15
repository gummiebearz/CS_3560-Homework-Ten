import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class UserTest {
    private static final String NAME = "John Doe";
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

    @Test
    void testGetUsername() {
        User user = new User(NAME);

        assertEquals(NAME, user.getName());
    }

    @Test
    void testGetChatHistoryIsNotNull() {
        User user = new User(NAME);

        assertNotNull(user.getChatHistory());
    }

    @Test
    void testEachUserHasOwnChatHistory() {
        User firstUser = new User("First User");
        User secondUser = new User("Second User");

        assertNotSame(firstUser.getChatHistory(), secondUser.getChatHistory());
    }

    @Test
    void testChatHistoryBelongsToItsUser() {
        User firstUser = new User("First User");
        User secondUser = new User("Second User");

        Message sentToSecondUser = new Message(firstUser, List.of(secondUser), TIMESTAMP, "Hi " + secondUser.getName());
        Message sentToFirstUser = new Message(secondUser, List.of(firstUser), TIMESTAMP, "Hi " + firstUser.getName());

        assertSame(sentToSecondUser, firstUser.getChatHistory().getLastMessageSent());
        assertSame(sentToFirstUser, secondUser.getChatHistory().getLastMessageSent());
    }
}
