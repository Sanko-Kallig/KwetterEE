package interfaces;

import models.Kweet;
import models.User;

import java.util.List;

public interface IKweetDAO {
    void delete(Kweet kweetToDelete);

    Kweet get(int kweetId);

    List<Kweet> getAll();

    Void timeline(User user1);

    Void latest(User user1, int i);

    Void search(String people);

    void create(Kweet kweet2);

    void like(Kweet kweet1, User user1);
}
