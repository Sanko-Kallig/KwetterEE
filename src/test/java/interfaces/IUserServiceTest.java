package interfaces;

import DAO.RelationDAO;
import DAO.UserDAO;
import models.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.UserService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static junit.framework.TestCase.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class IUserServiceTest {
    User user1;
    User user2;
    UserGroup userGroup;
    Relation relation1;

    private static EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    @InjectMocks
    UserService userService;

    UserDAO userDAO;
    RelationDAO relationDAO;

    @Test
    public void ServiceTest() {
        assertNotNull(userService);
    }
    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("TestDB");
        userDAO = mock(UserDAO.class);
        relationDAO = mock(RelationDAO.class);
        entityManager = entityManagerFactory.createEntityManager();
        user1 = new User(1, null, "user1", null, null, null, "password");
        user2 = new User(2, null, "user2", null, null, null, "password");
        entityManager.getTransaction().begin();
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.getTransaction().commit();
        userGroup = new UserGroup();
        entityManager.persist(userGroup);



        when(userDAO.getUser(1)).thenReturn(user1);
        when(userDAO.getUser(2)).thenReturn(user2);
        when(userDAO.getUser(20)).thenReturn(null);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createUser() throws Exception {
        User user3 = new User(3, null, "user3", null, null, null, "password");
        userService.createUser(user3);
    }

    @Test(expected = NullPointerException.class)
    public void createUserNoUsernameException() throws Exception {
        User user3 = new User(3, null, null, null, null, null, "password");
        userService.createUser(user3);
    }

    @Test(expected = NullPointerException.class)
    public void createUserEmptyUsernameException() throws Exception {
        User user3 = new User(3, null, "", null, null, null, "password");
        userService.createUser(user3);
    }

    @Test(expected = NullPointerException.class)
    public void createUserNoPasswordException() throws Exception {
        User user3 = new User(3, null, "user3", null, null, null, null);
        userService.createUser(user3);
    }

    @Test(expected = NullPointerException.class)
    public void createUserEmptyPasswordException() throws Exception {
        User user3 = new User(3, null, "user3", null, null, null, " ");
        userService.createUser(user3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserNameAlreadyExists() throws Exception {
        User user3 = new User(3, null, "user1", null, null, null, "password");
        userService.createUser(user3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserIdAlreadyExists() throws Exception {
        User user3 = new User(1, null, "user3", null, null, null, "password");
        userService.createUser(user3);
    }

    @Test
    public void updateUser() throws Exception {
        User user4 = new User(1, null, "user1", null, "user1.nl/", null, "password");
        userService.updateUser(user4);
        Assert.assertEquals("Web isn't set for user1.", "user1.nl/", userService.getUser(1).getWeb());
    }

    @Test(expected = NullPointerException.class)
    public void updateUserUnknownUserException() throws Exception {
        User user4 = new User(4, null, "user4", null, null, null, "password");
        userService.updateUser(user4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserExistingUserNameException() throws Exception {
        User updateUser = new User(1, null, "user2", null, null, null, "password");
        userService.updateUser(updateUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserChangeGroupNameException() throws Exception {
        UserGroup group = new UserGroup();
        group.setGroupName("Moderator");
        User updateUser = new User(1, null, "user1", null, null, null, "password");
        userService.updateUser(updateUser);
    }

    @Test
    public void getUser() throws Exception {
        Assert.assertEquals("Unexpected user received instead of user1.", user1, userService.getUser("user1"));
    }

    @Test
    public void getUser1() throws Exception {
        Assert.assertEquals("Unexpected user received instead of user1.", user1, userService.getUser(1));
    }

    @Test
    public void follow() throws Exception {
        userService.followUser(2, 1);
    }

    @Test(expected = NullPointerException.class)
    public void followNonExistingFollowerException() throws Exception {
        userService.followUser(20, 1);
    }

    @Test(expected = NullPointerException.class)
    public void followNonExistingFollowingException() throws Exception {
        userService.followUser(2, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void followAlreadyFollowedException() throws Exception {
        userService.followUser(1, 2);
    }

    @Test
    public void getFollowers() throws Exception {
        Assert.assertEquals("Follower not found.", user1, userService.getFollowers(2).get(0));
    }

    @Test (expected = NullPointerException.class)
    public void getFollowersNoFollowingException() throws Exception {
        userService.getFollowers(20);
    }

    @Test
    public void getFollowing() throws Exception {
        Assert.assertEquals("Follower not found.", user2, userService.getFollowing(1).get(0));
    }

    @Test (expected = NullPointerException.class)
    public void getFollowingNoFollowerException() throws Exception {
        userService.getFollowing(20);
    }

}