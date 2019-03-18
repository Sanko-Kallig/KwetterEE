package interfaces;

import DAO.KweetDAO;
import models.Kweet;

import java.util.Collection;

public interface IKweetService {
    void createKweet(Kweet kweet2);

    void likeKweet(int i, int i1);

    Collection getTimeline(int i);

    Collection latestKweets(int i, int i1);

    KweetDAO searchKweet(String people);
}
