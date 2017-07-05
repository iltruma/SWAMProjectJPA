package it.unifi.ing.swam.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import it.unifi.ing.swam.dao.DriverDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Driver;
import it.unifi.ing.swam.model.Waybill;

@Model
public class AssignDriverPageController extends BasicController{

	@Inject
	private WaybillDao waybillDao;
	
	@Inject
	private DriverDao driverDao;
	
	private List<Driver> driversAvailable;
	
	private List<Waybill> waybills;
	
	@PostConstruct
	protected void initAssignDriverPage(){
		if(userSession.getUser().isOperator()) {
			throw new IllegalArgumentException("you cant view this page");
		}
		
		waybills = waybillDao.findUnassignedToDriver(userSession.getUser().getAgency());
		driversAvailable = driverDao.findAvailable(userSession.getUser().getAgency());
	
	}

	public List<Driver> getDriversAvailable() {
		return driversAvailable;
	}

	public List<Waybill> getWaybills() {
		return waybills;
	}
	
	
	
	
	
}
