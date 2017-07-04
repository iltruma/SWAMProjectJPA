package it.unifi.ing.swam.controller.strategy;

import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class CustomerStrategy extends RoleStrategy {

	protected CustomerStrategy(String wid, WaybillDao wd, User u) {
		super(wid, wd, u);
		
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
				
				if(!waybill.getSender().equals(user)){
					throw new IllegalStateException("you can't view this waybill");
				}
				
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("id not a number");
			}	
		}
		return waybill;
	}
	

}
