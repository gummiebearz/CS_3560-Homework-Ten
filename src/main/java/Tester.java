import java.util.Iterator;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        ChatServer server = new ChatServer();

        User mickey = new User("Mickey", server);
        User minnie = new User("Minnie", server);
        User goofie = new User("Goofie", server);

        server.registerUser(mickey);
        server.registerUser(minnie);
        server.registerUser(goofie);

        System.out.println("=== 1. Send to one user, then to many users ===");
        mickey.sendMessage(List.of(minnie), "Hi Minnie!");
        minnie.sendMessage(List.of(mickey, goofie), "Hello everyone!");
        printHistory(mickey);
        printHistory(minnie);
        printHistory(goofie);

        System.out.println("\n=== 2. Goofie blocks Mickey ===");
        server.blockUsers(goofie, List.of(mickey));
        mickey.sendMessage(List.of(minnie, goofie), "Hey both of you!");
        printHistory(minnie);   // receives it
        printHistory(goofie);   // does not receive it

        System.out.println("\n=== 3. Mickey undoes his last message ===");
        mickey.undoLastMessageSent();
        printHistory(mickey);   // message removed
        printHistory(minnie);   // still has it (undo affects the sender only)

        System.out.println("\n=== 4. Iterate Minnie's history by a specific user ===");
        printMessagesBy(minnie, mickey);
        printMessagesBy(minnie, goofie);
    }

    // Prints one user's chat history, marking each message as sent or received
    private static void printHistory(User owner) {
        System.out.println("--- " + owner.getName() + "'s chat history ---");

        List<Message> messages = owner.getChatHistory().getMessages();
        if(messages.isEmpty()) {
            System.out.println("  (no messages)");
            return;
        }

        for(Message message : messages) {
            String description;

            if(message.getSender().equals(owner)) {
                String recipientNames = "";
                for(User recipient : message.getRecipients()) {
                    if(!recipientNames.isEmpty()) {
                        recipientNames += ", ";
                    }
                    recipientNames += recipient.getName();
                }
                description = "Sent to " + recipientNames;
            } else {
                description = "Received from " + message.getSender().getName();
            }

            System.out.println("  " + description + ": " + message.getContent());
        }
    }

    // Uses the iterator to print the messages in owner's history that the given user sent or received
    private static void printMessagesBy(User owner, User userToSearchWith) {
        System.out.println("--- " + owner.getName() + "'s messages involving " + userToSearchWith.getName() + " ---");

        Iterator<Message> iterator = owner.iterator(userToSearchWith);
        if(!iterator.hasNext()) {
            System.out.println("  (no messages)");
            return;
        }

        while(iterator.hasNext()) {
            Message message = iterator.next();
            System.out.println("  " + message.getSender().getName() + ": " + message.getContent());
        }
    }
}
