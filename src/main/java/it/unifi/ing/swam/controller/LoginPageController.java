package it.unifi.ing.swam.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.bean.MissionBean;
import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

@Model
public class LoginPageController {

    @Inject
    private UserDao userDao;

    @Inject
    private UserSessionBean userSession;
    @Inject
    private CustomerBean customerBean;
    @Inject
    private MissionBean missionBean;

    private User userData;

    private String username;

    private String password;

    LoginPageController() {
        userData = ModelFactory.generateUser();
    }

    public String login() {
        userData.setSalt(userDao.findSaltByUsername(username));
        userData.setPassword(password);
        userData.setUsername(username);

        User loggedUser = userDao.findByLoginInfo(userData);
        if (loggedUser == null)
            throw new RuntimeException("Login Failed");

        userSession.setUser(null);
        userSession.setUser(loggedUser);
        return "home?faces-redirect=true";
    }

    public String logout() {
        customerBean.endConversation();
        missionBean.endConversation();
        userSession.setUser(null);
        return "home?faces-redirect=true";
    }

    public String goHome() {
        customerBean.endConversation();
        missionBean.endConversation();
        return "home?faces-redirect=true";
    }

    public User getUserData() {
        return userData;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
