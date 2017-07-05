package it.unifi.ing.swam.controller;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.ConversationBean;
import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.model.Fare;

public class ViewFarePageController extends BasicController {

	@Inject 
	private UserDao userDao;
	
	@Inject 
	private ConversationBean conversationBean;
	
	@Inject @HttpParam("fareid")
	private String fareId;

	private Fare fare;

	@PostConstruct
	protected void initFaresPage(){
		if(!userSession.getUser().isOperator()) {
			throw new IllegalArgumentException("you cant view this page");
		}
		if(StringUtils.isEmpty(fareId)) {
			throw new IllegalArgumentException("fare id is empty");
		}		
		
		List<Fare> result = userDao.findById(Long.valueOf(conversationBean.getCustomerId())).getCustomerRole().getFares();
		
		for(Fare f : result ){
			if (f.getId() == Long.valueOf(fareId)){
				fare = f;
			}
		}
		
		if (fare == null){
			throw new IllegalArgumentException("fare not found for this user");
		}
		
	}
	
	public Fare getFare() {
		return fare;
	}
	
	
	
}
