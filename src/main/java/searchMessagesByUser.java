import java.util.Iterator;
import java.util.List;

public class searchMessagesByUser implements Iterator<Message> {
    public searchMessagesByUser(List<Message> messages, User user) {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Message next() {
        return null;
    }
}
