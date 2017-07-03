package it.unifi.ing.swam.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.model.User;

@Model
public class LoginPageController {
	
	@Inject
	private UserDao userDao;
	
	@Inject
	private UserSessionBean userSession;

	
	public String login(User user) {
		User loggedUser = userDao.findByLoginInfo(user);
		if( loggedUser == null ) {
			throw new RuntimeException("Login Failed");
		}
		
		userSession.setUser(loggedUser);
		return "Login Successfull";
	}

}
