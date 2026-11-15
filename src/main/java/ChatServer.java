import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatServer implements ChatMediator {
    private Map<User, List<User>> blockList;
    private List<User> registeredUsers;

    public ChatServer() {
        this.registeredUsers = new ArrayList<>();
    }

    @Override
    public void registerUser(User userToRegister) {
        this.registeredUsers.add(userToRegister);
    }

    @Override
    public void unregisterUser(User userToUnregister) {}

    @Override
    public void sendMessage(Message messageToSend) {
        for(User recipient : messageToSend.getRecipients()) {
            if(this.registeredUsers.contains(recipient)) {
                recipient.receiveMessage(messageToSend);
            }
        }
    }

    @Override
    public void blockUser(User user, User userToBlock) {}

    @Override
    public void unblockUser(User user, User userToUnblock) {}

    @Override
    public void unsendMessage(Message messageToSend) {}
}
