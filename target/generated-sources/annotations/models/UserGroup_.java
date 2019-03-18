package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.User;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2019-03-18T18:09:58")
@StaticMetamodel(UserGroup.class)
public class UserGroup_ { 

    public static volatile SingularAttribute<UserGroup, String> groupName;
    public static volatile SetAttribute<UserGroup, User> users;

}