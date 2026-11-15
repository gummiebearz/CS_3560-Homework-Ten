import java.util.List;
import java.util.Map;

public class ChatServer implements ChatMediator {
    private Map<User, List<User>> blockList;

    public ChatServer() {}

    @Override
    public void registerUser(User userToRegister) {}

    @Override
    public void unregisterUser(User userToUnregister) {}

    @Override
    public void sendMessage(Message messageToSend) {}

    @Override
    public void blockUser(User user, User userToBlock) {}

    @Override
    public void unblockUser(User user, User userToUnblock) {}

    @Override
    public void unsendMessage(Message messageToSend) {}
}
