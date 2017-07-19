package it.unifi.ing.swam.controller.strategy;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;

import it.unifi.ing.swam.dao.AgencyDao;
import it.unifi.ing.swam.dao.ItemDao;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Role;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@Dependent
public abstract class RoleStrategy {

	protected String waybillId;

	@Inject
	protected WaybillDao waybillDao;

	@Inject
	protected AgencyDao agencyDao;

	@Inject
	protected ItemDao itemDao;

	@Inject
	protected UserDao userDao;

	@Inject
	protected MissionDao missionDao;

	protected User user;
	protected Waybill waybill;


	public RoleStrategy() {}

	public abstract Waybill initWaybill();

	public static RoleStrategy getStrategy(Instance<RoleStrategy> roleStrategyInstance, Role r, User u, String waybillId) {
		RoleStrategy rs;
		if (r.isCustomer())
			rs = roleStrategyInstance.select(CustomerStrategy.class).get();
		else if (r.isOperator())
			rs = roleStrategyInstance.select(OperatorStrategy.class).get();
		else if (r.isDriver())
			rs = roleStrategyInstance.select(DriverStrategy.class).get();
		else
			throw new IllegalArgumentException("role not found");
		rs.init(u, waybillId);
		return rs;
	}

	public void init(User u, String wid){
		user = u;
		waybillId = wid;
	}

	public Long getSender() {
		if (waybill.getSender() != null)
			return waybill.getSender().getId();
		else
			return null;
	}

	public Long getAgency() {
		if (waybill.getReceiver().getDestinationAgency() != null)
			return waybill.getReceiver().getDestinationAgency().getId();
		else
			return null;
	}

	public Long getNewItem() {
		return null;
	}

	public void setSender(Long id) {
		throw new UnsupportedOperationException();
	}

	public void setAgency(Long id) {
		throw new UnsupportedOperationException();
	}

	public void setNewItem(Long id) {
		throw new UnsupportedOperationException();
	}

	public void checkEdit() {
		throw new UnsupportedOperationException();
	}

	public void setSignAndTracking() {
		throw new UnsupportedOperationException();
	}

	@Transactional
	public void save() {
		Float r = waybill.getSender().getCustomerRole().getFares().get(0).getFunctionCost();
		waybill.setCost((waybill.getLoad().getTotalVolume()+waybill.getLoad().getTotalWeight())*r/4.0F );
		waybillDao.save(waybill);
	}

	public Waybill getWaybill() {
		return waybill;
	}

	@Transactional
	public void delete() {
		throw new UnsupportedOperationException();
	}

}
