package models;

import javax.persistence.*;
import java.util.HashSet;


@Entity
@Table(name = "usergroup")
@NamedQueries({
@NamedQuery(name = "usergroup.all", query = "SELECT g FROM UserGroup g"),
@NamedQuery(name = "usergroup.get", query = "SELECT g from UserGroup g WHERE g.groupName = :groupName")})
public class UserGroup {
    @Id
    private String groupName;

    @OneToMany
    private HashSet<User> users;

    public UserGroup() {
        this.groupName = "User";
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public HashSet<User> getUsers() {
        return users;
    }

    public void setUsers(User user) {
            users.add(user);
    }
}