package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.controller.strategy.RoleStrategy;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@ViewScoped
public class EditCustomerPageController extends BasicController {
	
	@Inject 
	private UserDao userDao;
	
	private User customer;
	
	@Inject @HttpParam("id")
	private String customerId;
	
	@Inject
	@HttpParam("add")
	private String addFlag;
	
	@PostConstruct
	protected void initEditCustomerPage(){

		if(!userSession.getUser().isOperator()) {
			throw new IllegalArgumentException("you cant view this page");
		}
		if(StringUtils.isEmpty(customerId)) {
			throw new IllegalArgumentException("customer id is empty");
		}
		
		customer = userDao.findById(Long.valueOf(customerId));
		
		if(customer == null && Boolean.getBoolean(addFlag)) {
			customer = ModelFactory.generateUser();
			customer.addRole(ModelFactory.generateCustomer());
			customer.setAgency(userSession.getUser().getAgency());
		} 
		
		if(customer == null){
			throw new IllegalStateException("customer does not exist in database");
		} else if (customer.getAgency().equals(userSession.getUser().getAgency())){
			throw new IllegalStateException("customer not Editable");
		}
		
	}
	
	public User getCustomer() {
		return customer;
	}
	
	@Transactional
	public String save() {
		 userDao.save(customer);
		 return "ViewCustomerPage" + customer.getId() + customer.getCustomerRole().getId();
	}
	

}
