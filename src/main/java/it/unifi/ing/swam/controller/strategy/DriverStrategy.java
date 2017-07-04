package it.unifi.ing.swam.controller.strategy;

import java.util.Calendar;

import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class DriverStrategy extends RoleStrategy {
	
	private MissionDao missionDao;

	protected DriverStrategy(String wid, WaybillDao wd, MissionDao md, User u) {
		super(wid, wd, u);
		this.missionDao = md;
		
	}
	
	@Override
	public Waybill initWaybill(){
		Waybill waybill = null;
		if(this.waybillId == null ) {
			waybill = ModelFactory.generateWaybill();
			waybill.setSender(user);
		} else {
			try {
				Long id = Long.valueOf(this.waybillId);
				waybill = waybillDao.findById(id);
				
				if(!missionDao.findByDriverAndDate(user, Calendar.getInstance()).getWaybills().contains(waybill)){
					throw new IllegalStateException("you can't view this waybill");
				}
				
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("id not a number");
			}	
		}
		return waybill;
	}

}
