package DAO;

import interfaces.IUserDAO;
import models.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class UserDAO implements IUserDAO {

    @PersistenceContext
    EntityManager em;

    public void createUser(User user) {
        em.persist(user);
    }

    public void update(User user) {
        em.merge(user);
    }

    public User getUser(long id) {
        try {
            Query query = em.createNamedQuery("user.getById");
            query.setParameter("userid", id);
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public User getUser(String name) {
        try {
            Query query = em.createNamedQuery("user.getByName");
            query.setParameter("username", name);
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<User> getAll() {
        return em.createNamedQuery("user.all").getResultList();
    }

    public UserGroup GetUserGroup(String name) {
        try {
            return em.find(UserGroup.class, name);
        } catch (NoResultException ex) {
            return null;
        }
    }

    public void updateGroup(UserGroup group) {
        em.merge(group);
    }

    public void AddGroupUsers(UserGroup userGroup, List<User> users)
    {

    }
}
