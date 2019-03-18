package DAO;

import models.Relation;
import models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class RelationDAO {

    @PersistenceContext
    EntityManager em;

    public void followUser(Relation relation) {
        em.persist(relation);
    }

    public List<Relation> getFollowers(User user) {
        Query query = em.createNamedQuery("relation.followers");
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<Relation> getFollowing(User user){
        Query query = em.createNamedQuery("relation.following");
        query.setParameter("user", user);
        return query.getResultList();
    }
}
