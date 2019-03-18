package interfaces;

import models.User;

import java.util.List;


public interface IUserService {
    public void createUser(User user);
    public void updateUser(User user);
    public User getUser(String name);
    public User getUser(int id);
    public void followUser(int follower, int following) throws Exception;
    public List<User> getFollowers(int following);
    public List<User> getFollowing(int follower);
}
