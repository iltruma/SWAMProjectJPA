package it.unifi.ing.swam.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import it.unifi.ing.swam.model.User;

@SessionScoped
@Named
public class UserSessionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

}
