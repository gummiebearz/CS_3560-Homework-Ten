import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer implements ChatMediator {
    private List<User> registeredUsers;
    private Map<User, List<User>> blockList;

    public ChatServer() {
        this.registeredUsers = new ArrayList<>();
        this.blockList = new HashMap<>();
    }

    @Override
    public void registerUser(User userToRegister) {
        if(!registeredUsers.contains(userToRegister)) {
            this.registeredUsers.add(userToRegister);
        }
    }

    @Override
    public void unregisterUser(User userToUnregister) {
        this.registeredUsers.removeIf(user -> user.equals(userToUnregister));
    }

    public List<User> getRegisteredUsers() {
        return this.registeredUsers;
    }

    @Override
    public void sendMessage(Message messageToSend) {}

    @Override
    public void blockUsers(User currentUser, List<User> usersToBlock) {}
}
