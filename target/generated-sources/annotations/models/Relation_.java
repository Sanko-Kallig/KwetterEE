package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.User;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2019-03-18T13:47:48")
@StaticMetamodel(Relation.class)
public class Relation_ { 

    public static volatile SingularAttribute<Relation, User> follower;
    public static volatile SingularAttribute<Relation, User> following;

}