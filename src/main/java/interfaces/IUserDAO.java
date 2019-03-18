package interfaces;

import models.*;
import java.util.List;

public interface IUserDAO {
    void createUser(User user);
    void update(User user);
    User getUser(long id);
    User getUser(String name);
    List<User> getAll();
    UserGroup GetUserGroup(String name);

    void updateGroup(UserGroup group);
}
