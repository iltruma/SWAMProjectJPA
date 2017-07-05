package it.unifi.ing.swam.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.ConversationBean;
import it.unifi.ing.swam.dao.CustomerDao;
import it.unifi.ing.swam.model.Customer;
import it.unifi.ing.swam.model.User;

public class CustomersPageController extends BasicController{
	
	@Inject 
	private CustomerDao customerDao;
	
	@Inject 
	private ConversationBean conversationBean;
	
	private List<User> customers;


	@PostConstruct
	protected void initCustomersPage(){
		if(userSession.getUser().isOperator()) {
			throw new IllegalArgumentException("you cant view this page");
		}
		List<Customer> results = customerDao.findByOperator(userSession.getUser());
		customers = new ArrayList<User>();
		
		for(Customer c: results){
			customers.add(c.getOwner());
		}
		
	}
	
	public List<User> getCustomers() {
		return customers;
	}
	
	public void selectCustomer(User u){
		conversationBean.setCustomer(u);
		conversationBean.initConversation();
	}
	
	
}
