package it.unifi.ing.swam.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.DriverDao;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Driver;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.Waybill;

@Model
public class MissionAssignPageController extends BasicController{
		
	@Inject @HttpParam("driver_id")
	private String driverId;
	
	@Inject 
	private MissionDao missionDao;
	
	private Mission mission;
		
	@PostConstruct
	protected void initMissionAssignPage(){
		if(!userSession.getUser().isOperator()) {
			throw new IllegalArgumentException("you cant view this page");
		}
		
		mission = missionDao.findByDriverAndDate(userSession.getUser(), date)
			
			
		
	}


}
