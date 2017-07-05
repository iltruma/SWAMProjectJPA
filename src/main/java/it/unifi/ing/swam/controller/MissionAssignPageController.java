package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.ConversationBean;
import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.Waybill;

@ViewScoped
public class MissionAssignPageController extends BasicController{
		
	@Inject @HttpParam("driver_id")
	private String driverId;
	
	@Inject 
	private MissionDao missionDao;
	
	@Inject 
	private ConversationBean conversationBean;
	
	private Mission mission;
		
	@PostConstruct
	protected void initMissionAssignPage(){
		if(!userSession.getUser().isOperator()) {
			throw new IllegalArgumentException("you cant view this page");
		}
		
		mission = conversationBean.getMission();
		
		if (mission == null){
			throw new IllegalArgumentException("mission not found!!");
		}

	}

	public Mission getMission() {
		return mission;
	}
	
	public void remove(Waybill w){
		mission.getWaybills().remove(w);
	}
	
	
	public void exit(){
		missionDao.save(mission);
		conversationBean.endConversation();
	}
	


}
