// Mediator interface

public interface ChatMediator {
    public void registerUser(User userToRegister);
    public void unregisterUser(User userToUnregister);
    public void sendMessage(Message messageToSend);
    public void blockUser(User user, User userToBlock);
    public void unblockUser(User user, User userToUnblock);
    public void unsendMessage(Message messageToUnsend);
}