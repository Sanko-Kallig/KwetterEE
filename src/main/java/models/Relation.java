package models;

import javax.persistence.*;

/**
 * Created by Qun on 28-2-2018.
 */
@Entity
@Table(name = "relation")
@NamedQueries({@NamedQuery(name = "relation.followers", query = "SELECT r FROM Relation r WHERE r.following = :user"),
        @NamedQuery(name = "relation.following", query = "SELECT r FROM Relation r WHERE r.follower = :user")})
public class Relation {
    @ManyToOne
    @Id
    private User follower;
    @ManyToOne
    @Id
    private User following;

    public Relation() {

    }

    public Relation(User follower, User following) throws Exception {
        if (follower.equals(following))
            throw new IllegalArgumentException("Follower was equal to following");
        this.follower = follower;
        this.following = following;
    }

    public User getFollower() {
        return follower;
    }

    public User getFollowing() {
        return following;
    }
}