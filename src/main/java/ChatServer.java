import java.util.List;
import java.util.Map;

public class ChatServer implements ChatMediator {
    private List<User> registeredUsers;
    private Map<User, List<User>> blockList;

    public ChatServer() {}

    @Override
    public void registerUser(User userToRegister) {
    }

    @Override
    public void unregisterUser(User userToUnregister) {
    }

    public List<User> getRegisteredUsers() {
        return null;
    }
}
