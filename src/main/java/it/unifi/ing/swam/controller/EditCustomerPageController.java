package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import it.unifi.ing.swam.bean.ConversationCustomerBean;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

@ViewScoped
public class EditCustomerPageController extends BasicController {
	
	@Inject 
	private UserDao userDao;
	
	@Inject 
	private ConversationCustomerBean conversationBean;
	
	@PostConstruct
	protected void initEditCustomerPage(){

		if(!userSession.getUser().isOperator()) {
			throw new IllegalArgumentException("you cant view this page");
		}
				
		if(conversationBean.getCustomer() == null) {
			conversationBean.setCustomer(ModelFactory.generateUser());
			conversationBean.getCustomer().addRole(ModelFactory.generateCustomer());
			conversationBean.getCustomer().setAgency(userSession.getUser().getAgency());
		} 
		
		if (!conversationBean.getCustomer().getAgency().equals(userSession.getUser().getAgency())){
			throw new IllegalStateException("customer not Editable");
		}
		
	}
	
	public User getCustomer() {
		return conversationBean.getCustomer();
	}
	
	@Transactional
	public String save() {
		 userDao.save(conversationBean.getCustomer());
		 return "ViewCustomerPage";
	}
	

}
