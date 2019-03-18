package services;

import DAO.UserDAO;
import interfaces.IKweetDAO;
import interfaces.IUserDAO;
import models.Kweet;
import models.User;
import models.UserGroup;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Qun on 28-2-2018.
 */
@Stateless
public class ModService {

    @Inject
    IKweetDAO kweetDAO;

    @Inject
    IUserDAO userDAO;

    /**
     * Lets a mod or admin to change the role of a user
     *
     * @param userId the id of the user where the role will be changed of
     * @param role   the role the user get
     */
    public void changeRole(int userId, String role) throws NullPointerException {
        User user = userDAO.getUser(userId);

        if (user == null)
            throw new NullPointerException("Can't find the user!");

        UserGroup group = userDAO.GetUserGroup(role);

        if(group == null)
        {
              group = new UserGroup();
        }

        group.setGroupName(role);
        group.setUsers(user);
        userDAO.updateGroup(group);
    }

    /**
     * Gives a list of all users with its roles for a mod or admin
     *
     * @return the list of all users with its roles
     */
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    /**
     * Gives a list of all kweets for a mod or admin
     *
     * @return the list of all kweets
     */
    public List<Kweet> getAllKweets() {
        return kweetDAO.getAll();
    }

    /**
     * Lets a mod or admin delete a kweet
     *
     * @param kweetId the id of the kweet that has to be deleted
     */
    public void deleteKweet(int kweetId) throws NullPointerException {
        Kweet kweetToDelete = kweetDAO.get(kweetId);

        if (kweetToDelete == null)
            throw new NullPointerException("Can't find the kweet!");

        kweetDAO.delete(kweetToDelete);
    }
}
