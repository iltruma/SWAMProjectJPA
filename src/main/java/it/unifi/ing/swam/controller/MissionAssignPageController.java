package it.unifi.ing.swam.controller;

import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.DriverDao;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.model.Driver;
import it.unifi.ing.swam.model.Mission;

@Model
public class MissionAssignPageController extends BasicController{
		
	@Inject @HttpParam("driver_id")
	private String driverId;
	
	@Inject 
	private MissionDao missionDao;
	
	@Inject 
	private DriverDao driverDao;
	
	private Mission mission;
		
	@PostConstruct
	protected void initMissionAssignPage(){
		if(!userSession.getUser().isOperator()) {
			throw new IllegalArgumentException("you cant view this page");
		}
		
		Driver driver = driverDao.findById(Long.valueOf(driverId)); 
		
		if (driver == null){
			throw new IllegalArgumentException("driver is not in the database");
		}
		
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		mission = missionDao.findByDriverAndDate(driver.getOwner(), tomorrow);

	}

	public Mission getMission() {
		return mission;
	}


}
