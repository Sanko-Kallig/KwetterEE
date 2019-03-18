package services;

import interfaces.*;
import models.Relation;
import models.User;
import models.UserGroup;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UserService implements IUserService {

    @Inject
    IUserDAO userDAO;

    @Inject
    IRelationDAO relationDAO;
    /**
     * Creates a user of Kwetter in the system.
     *
     * @param user the user that will be created
     * @throws NullPointerException     when username or password are null
     * @throws IllegalArgumentException when the user already exists
     */
    public void createUser(User user) throws NullPointerException, IllegalArgumentException {
        if (user.getName() == null || user.getName() == "")
            throw new NullPointerException("Username can't be null!");
        if (user.getPassword() == null || user.getPassword() == "")
            throw new NullPointerException("Password can't be null!");
        if (userDAO.getUser(user.getName()) != null || userDAO.getUser(user.getId()) != null)
            throw new IllegalArgumentException("User already exists!");

        userDAO.createUser(user);
    }

    /**
     * Updates a user of Kwetter in the system.
     *
     * @param user the updated user
     * @throws NullPointerException     when user doesn't exist
     * @throws IllegalArgumentException when the username already exist or the group name was different
     */
    public void updateUser(User user) throws NullPointerException, IllegalArgumentException {
        User userToUpdate = userDAO.getUser(user.getId());
        if (userToUpdate == null)
            throw new NullPointerException("Can't find the requested user to update.");

        if (!userToUpdate.getName().equals(user.getName())) {
            if (userDAO.getUser(user.getName()) != null)
                throw new IllegalArgumentException("Username already exists!");
        }

        userDAO.update(user);
    }

    /**
     * Searches for a user with the given name
     * Returns null if no user can be found
     *
     * @param name the name of the requested user
     * @return the found user based on the given name
     */
    public User getUser(String name) {
        return userDAO.getUser(name);
    }

    /**
     * Searches for a user with the given id
     * Returns null if no user can be found
     *
     * @param id the id of the requested user
     * @return the found user based on the given id
     */
    public User getUser(int id) {
        return userDAO.getUser(id);
    }

    /**
     * Makes a relation where one user follows another
     *
     * @param follower  the id of the user that is the follower
     * @param following the id of the user that gets followed
     * @throws Exception when the user follows itself, when a user can't be found or when the user already follows the user
     */
    public void followUser(int follower, int following) throws Exception {
        User followerUser = userDAO.getUser(follower);
        if (followerUser == null)
            throw new NullPointerException("Can't find the user that is the follower!");

        User followingUser = userDAO.getUser(following);
        if (followingUser == null)
            throw new NullPointerException("Can't find the user that is the following!");

        for( Relation relation: relationDAO.getFollowing(followerUser))
            if (relation.getFollowing().getId() == followingUser.getId())
                throw new IllegalArgumentException("Follower already follows the following.");

        Relation relation = new Relation(followerUser, followingUser);
        relationDAO.followUser(relation);
    }

    /**
     * Returns a list of users that follow the given user
     *
     * @param following the id of the user that is being followed
     * @return the list of followers
     */
    public List<User> getFollowers(int following) {
        User followingUser = userDAO.getUser(following);
        ArrayList<User> followers = new ArrayList<User>();

        if (followingUser == null)
            throw new NullPointerException("Following can't be found.");

        for (Relation relation : relationDAO.getFollowers(followingUser))
            followers.add(relation.getFollower());

        return Collections.unmodifiableList(followers);
    }

    /**
     * Returns a list of users that the given user follows
     *
     * @param follower the id of the user that follows
     * @return the list of following users
     */
    public List<User> getFollowing(int follower) {
        User followerUser = userDAO.getUser(follower);
        ArrayList<User> following = new ArrayList<User>();

        if (followerUser == null)
            throw new NullPointerException("Follower can't be found.");

        for (Relation relation : relationDAO.getFollowing(followerUser))
            following.add(relation.getFollowing());

        return Collections.unmodifiableList(following);
    }

    public UserGroup getUserGroup(String name)
    {
        return userDAO.GetUserGroup(name);
    }
}