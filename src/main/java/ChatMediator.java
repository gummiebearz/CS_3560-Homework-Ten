import java.util.List;

public interface ChatMediator {
    void registerUser(User userToRegister);
    void unregisterUser(User userToUnregister);
    void sendMessage(Message messageToSend);
    void blockUsers(User currentUser, List<User> usersToBlock);
}
