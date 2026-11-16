import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class searchMessagesByUser implements Iterator<Message> {
    private List<Message> messages;
    private User user;
    private int index;

    public searchMessagesByUser(List<Message> messages, User user) {
        this.messages = messages;
        this.user = user;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        // Skips messages that the user neither sends nor receives
        while(index < messages.size()) {
            if(messages.get(index).getSender().equals(user) || messages.get(index).getRecipients().contains(user)) {
                return true;
            }

            ++index;
        }

        return false;
    }

    @Override
    public Message next() {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }

        return messages.get(index++);
    }
}
