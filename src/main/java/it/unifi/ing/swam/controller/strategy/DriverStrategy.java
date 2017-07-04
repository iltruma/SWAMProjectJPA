package it.unifi.ing.swam.controller.strategy;

import java.util.Calendar;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;

import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@Dependent
public class DriverStrategy extends RoleStrategy {
	
	@Inject
	private MissionDao missionDao;

	protected DriverStrategy(String wid, User u) {
		super(wid, u);
		
	}
	
	@Override
	public Waybill initWaybill(){
		if(this.waybillId == null ) {
			throw new IllegalStateException("Driver can't create a waybill");
		} else {
			try {
				Long id = Long.valueOf(this.waybillId);
				waybill = waybillDao.findById(id);
				
				if(!missionDao.findByDriverAndDate(user, Calendar.getInstance()).getWaybills().contains(waybill)){
					throw new IllegalStateException("you can't access this waybill");
				}
				
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("id not a number");
			}	
		}
		return waybill;
	}
	
	public void setSignAndTracking(){
		waybill.setSign(true);
		waybill.setTracking(Tracking.DELIVERED);
		
	}
	
	@Transactional
	public String save(){
		waybillDao.save(waybill);
		return "ViewPage" + waybill.getId() + user.getDriverRole().getId() ;
	}

}
