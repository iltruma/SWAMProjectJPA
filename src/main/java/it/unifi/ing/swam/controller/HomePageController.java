package it.unifi.ing.swam.controller;

import java.util.Calendar;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Waybill;

@Model
public class HomePageController {

	@Inject
	private WaybillDao waybillDao;
	@Inject
	private MissionDao missionDao;

	@Inject
	private UserSessionBean userSession;
	
	public void checkIsLogged(){
		if(!userSession.isLoggedIn()){
			throw new RuntimeException("No User logged in!");
		}
	}

	public List<Waybill> getProposedWaybillCustomer(){
		//Si suppone che il controllo venga fatto nella pagina JSF
		return waybillDao.findProposedBySender(userSession.getUser());
	}

	public List<Waybill> getValidatedWaybillCustomer(){
		//Si suppone che il controllo venga fatto nella pagina JSF
		return waybillDao.findValidatedBySender(userSession.getUser());
	}

	public List<Waybill> getProposedWaybillOperator(){
		//Si suppone che il controllo venga fatto nella pagina JSF
		return waybillDao.findProposedByAgency(userSession.getUser().getAgency());
	}

	public List<Waybill> getUnassignedToDriver(){
		//Si suppone che il controllo venga fatto nella pagina JSF
		return waybillDao.findUnassignedToDriver(userSession.getUser().getAgency());
	}

	public List<Waybill> getTodayMission(){
		//Si suppone che il controllo venga fatto nella pagina JSF
		return missionDao.findByDriverAndDate(userSession.getUser(), Calendar.getInstance()).getWaybills();
	}

}
