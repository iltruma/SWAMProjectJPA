package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.ConversationCustomerBean;
import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.FareDao;
import it.unifi.ing.swam.model.Fare;

@Model
public class ViewFarePageController extends BasicController {

	@Inject 
	private ConversationCustomerBean conversationBean;
	
	@Inject 
	private FareDao fareDao;
	
	@Inject @HttpParam("fare_id")
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
		
		fare = fareDao.findById(Long.valueOf(fareId));
		
		if (fare == null){
			throw new IllegalArgumentException("fare not found");
		}
		
		if (!conversationBean.getCustomer().getCustomerRole().getFares().contains(fare)){
			throw new IllegalArgumentException("fare not found for this customer");
		}
		
	}
	
	public Fare getFare() {
		return fare;
	}
	
	
	
}
