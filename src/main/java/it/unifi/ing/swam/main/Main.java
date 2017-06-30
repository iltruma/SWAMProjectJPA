package it.unifi.ing.swam.main;

import javax.inject.Inject;

import it.unifi.ing.swam.dao.UserDao;

public class Main {
	

	@Inject
	transient UserDao userDao;
	
	@Inject
	transient UserDao wayBill;

    public static void main(String[] args) {

//        UserDao dao = new UserDao();
//
//        List<User> userList = dao.advancedSearch("", "name_1", "", "");
//
//        for (User us : userList) {
//            System.out
//                    .println(us.getUsername() + " " + us.getName() + " " + us.getPhone() + " " + us.getEmail() + "\n");
//        }
    	
    	


        System.exit(0);
    }

}
