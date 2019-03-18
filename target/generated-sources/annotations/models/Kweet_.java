package models;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.User;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2019-03-18T18:11:38")
@StaticMetamodel(Kweet.class)
public class Kweet_ { 

    public static volatile SingularAttribute<Kweet, Calendar> date;
    public static volatile SingularAttribute<Kweet, Long> id;
    public static volatile SingularAttribute<Kweet, String> message;
    public static volatile SingularAttribute<Kweet, User> user;
    public static volatile SetAttribute<Kweet, User> likers;

}