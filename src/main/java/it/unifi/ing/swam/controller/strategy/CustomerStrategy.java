package it.unifi.ing.swam.controller.strategy;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Item;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@Dependent
public class CustomerStrategy extends RoleStrategy {

	protected CustomerStrategy(String wid, User u) {
		super(wid, u);
		
	}
	
	@Override
	public Waybill initWaybill(){
		if(this.waybillId == null ) {
			waybill = ModelFactory.generateWaybill();
			waybill.setSender(user);
		} else {
			try {
				Long id = Long.valueOf(this.waybillId);
				waybill = waybillDao.findById(id);	
				
				if(waybill == null) {
					throw new IllegalStateException("waybill does not exist in database");
				}
				
				if(!waybill.getSender().equals(user)){
					throw new IllegalStateException("you can't access this waybill");
				}
				
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("id not a number");
			}	
		}
		return waybill;
	}
	
	@Override
	public void checkEdit() {
		if(waybill.getOperator() != null && !user.getCustomerRole().isActive()  &&
		   !waybill.getTracking().equals(Tracking.IDLE)){
			throw new IllegalStateException("you can't edit this waybill");			
		}	
	}
	
	public void setAgency(Long id){
		Agency a = agencyDao.findById(id);
		if(a == null){
			throw new IllegalArgumentException("id not found");
		}
		waybill.getReceiver().setDestinationAgency(a);
	}
	
	public void addItem(Long id){
		Item i = itemDao.findById(id);
		if(i == null){
			throw new IllegalArgumentException("id not found");
		}
		waybill.getLoad().addItem(i);
	}
	
	@Transactional
	public String save(){
		waybillDao.save(waybill);
		return "ViewPage" + waybill.getId() + user.getCustomerRole().getId() ;
	}

}
