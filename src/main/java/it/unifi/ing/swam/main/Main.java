package it.unifi.ing.swam.main;


import static org.mockito.Mockito.*;

import it.unifi.ing.swam.dao.*;
import it.unifi.ing.swam.model.*;

public class Main {
	

    public static void main(String[] args) {
    	
    	Integer user = mock(Integer.class);
    	
    	UserDao userDao = mock(UserDao.class);

    	OperatorDao operatorDao = mock(OperatorDao.class);
    	
    	CustomerDao customerDao = mock(CustomerDao.class);
    	
    	WaybillDao waybillDao = mock(WaybillDao.class);

//        UserDao dao = new UserDao();
//
//        List<User> userList = dao.advancedSearch("", "name_1", "", "");
//
//        for (User us : userList) {
//            System.out
//                    .println(us.getUsername() + " " + us.getName() + " " + us.getPhone() + " " + us.getEmail() + "\n");
//        }
    	
    	User operator = userDao.findByUsername("Operator_1");
    	Operator o = operatorDao.findByUserId(operator.getId());
    	User sender = userDao.findByUsername("Operator_1");
    	Customer c = customerDao.findByUserId(sender.getId());
    	Waybill w = ModelFactory.generateWaybill();
    	w.setSender(sender);
    	w.setOperator(operator);
    	
    	waybillDao.save(w);
   

        System.exit(0);
    }

}
