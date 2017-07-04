package it.unifi.ing.swam.controller.strategy;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Item;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@Dependent
public class OperatorStrategy extends RoleStrategy {

	protected OperatorStrategy(String wid, User u) {
		super(wid, u);

	}

	@Override
	public Waybill initWaybill() {
		if (this.waybillId == null) {
			waybill = ModelFactory.generateWaybill();
			waybill.setOperator(user);
		} else {
			try {
				Long id = Long.valueOf(this.waybillId);
				waybill = waybillDao.findById(id);

				if (!waybill.getOperator().equals(user)) {
					throw new IllegalStateException("you can't access this waybill");
				}

			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("id not a number");
			}
		}
		return waybill;
	}

	public void setCustomer(Long id){
		User sender = userDao.findById(id);
		if (sender == null){
			throw new IllegalArgumentException("id not found");
		} else if (!sender.isCustomer()){
			throw new IllegalArgumentException("the sender is not a customer");
		} else if (!sender.getCustomerRole().isActive()){
			throw new IllegalArgumentException("the sender is blocked!");
		}
		
		waybill.setSender(sender);
		
	}

	public void setAgency(Long id) {
		Agency a = agencyDao.findById(id);
		if (a == null) {
			throw new IllegalArgumentException("id not found");
		}
		waybill.getReceiver().setDestinationAgency(a);
	}

	public void addItem(Long id) {
		Item i = itemDao.findById(id);
		if (i == null) {
			throw new IllegalArgumentException("id not found");
		}
		waybill.getLoad().addItem(i);
	}
	
	@Transactional
	public String save(){
		waybill.setOperator(user);
		waybillDao.save(waybill);
		return "ViewPage" + waybill.getId() + user.getOperatorRole().getId() ;
	}
}
