package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.ConversationBean;
import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.FareDao;
import it.unifi.ing.swam.model.Fare;

@ViewScoped
public class EditFarePageController extends BasicController{
	
	@Inject
	private FareDao fareDao;
	
	@Inject 
	private ConversationBean conversationBean;
	
	@Inject @HttpParam("fare_id")
	private String fareId;
	
	private Fare fare;
	
	@PostConstruct
	protected void initEditFarePage(){

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
	
	@Transactional
	public String save() {
		 fareDao.save(fare);
		 return "ViewFarePage" + fare.getId().toString();
	}
	
}
