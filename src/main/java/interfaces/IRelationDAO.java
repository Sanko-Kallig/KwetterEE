package interfaces;

import models.Relation;
import models.User;

import java.util.List;

public interface IRelationDAO {
    public void followUser(Relation relation);
    public List<Relation> getFollowers(User user);
    public List<Relation> getFollowing(User user);
}
