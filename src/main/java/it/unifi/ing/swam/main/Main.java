package it.unifi.ing.swam.main;

import java.util.List;

import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.model.User;

public class Main {

    public static void main(String[] args) {

        UserDao dao = new UserDao();

        List<User> userList = dao.advancedSearch("", "name_1", "", "");

        for (User us : userList) {
            System.out
                    .println(us.getUsername() + " " + us.getName() + " " + us.getPhone() + " " + us.getEmail() + "\n");
        }

        System.exit(0);
    }

}
