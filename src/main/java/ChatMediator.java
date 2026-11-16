import java.util.List;

public interface ChatMediator {
    public void registerUser(User userToRegister);
    public void unregisterUser(User userToUnregister);
    public void sendMessage(Message messageToSend);
    public void blockUsers(User currentUser, List<User> usersToBlock);
}
