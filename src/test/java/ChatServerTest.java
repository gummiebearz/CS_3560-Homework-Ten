import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatServerTest {
    @Test
    void testNewServerHasNoRegisteredUsers() {
        ChatServer server = new ChatServer();
        assertTrue(server.getRegisteredUsers().isEmpty());
    }

    @Test
    void testRegisterUser() {
        ChatServer server = new ChatServer();
        User user = new User("New User");

        server.registerUser(user);

        assertTrue(server.getRegisteredUsers().contains(user));
    }

    @Test
    void testUnregisterUser() {
        ChatServer server = new ChatServer();
        User user = new User("New User");

        server.registerUser(user);
        server.unregisterUser(user);

        assertFalse(server.getRegisteredUsers().contains(user));
    }
}
