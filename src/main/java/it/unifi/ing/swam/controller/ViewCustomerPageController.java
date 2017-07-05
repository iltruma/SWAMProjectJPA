package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.model.User;

public class ViewCustomerPageController extends BasicController{
	
	@Inject 
	private UserDao userDao;
	
	private User customer;
	
	@Inject @HttpParam("id")
	private String customerId;


	@PostConstruct
	protected void initViewCustomerPage(){
		if(!userSession.getUser().isOperator()) {
			throw new IllegalArgumentException("you cant view this page");
		}
		if(StringUtils.isEmpty(customerId)) {
			throw new IllegalArgumentException("customer id is empty");
		}
		customer = userDao.findById(Long.valueOf(customerId));
		
		if(customer == null) {
			throw new IllegalArgumentException("customer not found");
		}	
		
	}
	
	public User getCustomer() {
		return customer;
	}
	
}
