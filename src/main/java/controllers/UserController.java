package controllers;

import interfaces.IUserService;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserController {

    @Inject
    IUserService userService;
}
