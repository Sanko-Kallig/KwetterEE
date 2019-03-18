package interfaces;

import DAO.KweetDAO;
import DAO.UserDAO;
import models.Kweet;
import models.User;
import models.UserGroup;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import services.KweetService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.mock;


import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IKweetServiceTestIT {
    User user1;
    User user2;

    private static EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    Kweet kweet1;

    @InjectMocks
    KweetService kweetService;

    KweetDAO kweetDAO;
    UserDAO userDAO;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("TestDB");

        user1 = new User(1, null, "user1", null, null, null, "hoi");
        user2 = new User(2, null, "user2", null, null, null, "hoi");
        kweet1 = new Kweet(1, "Hey people!", user1);

        List<Kweet> kweetList = new ArrayList<Kweet>();
        kweetList.add(kweet1);

        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new UserGroup());
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(kweet1);

        entityManager.getTransaction().commit();

        kweetService = new KweetService();

        userDAO = mock(UserDAO.class);
        kweetDAO = mock(KweetDAO.class);

        Calendar calendar = Calendar.getInstance();

        when(userDAO.getUser(1)).thenReturn(user1);
        when(userDAO.getUser(2)).thenReturn(user2);
        when(userDAO.getUser(20)).thenReturn(null);
        when(kweetDAO.get(1)).thenReturn(kweet1);
        when(kweetDAO.get(20)).thenReturn(null);
        when(kweetDAO.timeline(user1)).thenReturn(kweetList);
        when(kweetDAO.latest(user1, 1)).thenReturn(kweetList);
        when(kweetDAO.search("PEOPLE")).thenReturn(kweetList);
    }

    @After
    public void tearDown() throws Exception {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Relation").executeUpdate();
        entityManager.createQuery("DELETE FROM Kweet").executeUpdate();
        entityManager.createQuery("DELETE FROM User").executeUpdate();
        entityManager.createQuery("DELETE FROM UserGroup").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void createKweet() throws Exception {
        Kweet kweet2 = new Kweet(2, "Hey all!", user1);
        kweetService.createKweet(kweet2);
        Mockito.verify(kweetDAO, Mockito.times(1)).create(kweet2);
    }

    @Test
    public void likeKweet() throws Exception {
        kweetService.likeKweet(1, 1);
        Mockito.verify(userDAO, Mockito.times(1)).getUser(1);
        Mockito.verify(kweetDAO, Mockito.times(1)).get(1);
        Mockito.verify(kweetDAO, Mockito.times(1)).like(kweet1, user1);
    }

    @Test(expected = Exception.class)
    public void likeKweetAlreadyLikes() throws Exception {
        kweetService.likeKweet(1, 1);
        kweetService.likeKweet(1, 1);
    }

    @Test(expected = NullPointerException.class)
    public void likeKweetNonExistingUserException() throws Exception {
        kweetService.likeKweet(1, 20);
    }

    @Test(expected = NullPointerException.class)
    public void likeKweetNonExistingKweetException() throws Exception {
        kweetService.likeKweet(20, 1);
    }

    @Test
    public void getTimeline() throws Exception {
        Assert.assertEquals("Received a different amount of kweets than expected.", 1, kweetService.getTimeline(1).size());
    }

    @Test(expected = NullPointerException.class)
    public void getTimelineNonExistingUserException() throws Exception {
        kweetService.getTimeline(20);
    }

    @Test
    public void latestKweets() throws Exception {
        Assert.assertEquals("Received a different amount of kweets than expected.",1, kweetService.latestKweets(1, 1).size());
    }

    @Test(expected = NullPointerException.class)
    public void latestKweetsNonExistingUserException() throws Exception {
        kweetService.latestKweets(20, 20);
    }

    @Test
    public void searchKweet() throws Exception {
        Assert.assertEquals("Received a different kweet than expected.", kweet1, kweetService.searchKweet("PEOPLE").get(0));
    }

}
