package it.unifi.ing.swam.controller.strategy;

import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Role;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public abstract class RoleStrategy {

	protected String waybillId;
	protected WaybillDao waybillDao;

	protected User user;

	protected RoleStrategy(String wid, WaybillDao wd, User u) {
		this.waybillId = wid;
		this.waybillDao = wd;
		this.user = u;
	}

	public abstract Waybill initWaybill();

	public static RoleStrategy getStrategyFrom(Role r, String wid, WaybillDao wd, MissionDao md, User u) {
		if (r.isCustomer()) {
			return new CustomerStrategy(wid, wd, u);
		} else if (r.isOperator()) {
			return new OperatorStrategy(wid, wd, u);
		} else if (r.isDriver()) {
			return new DriverStrategy(wid, wd, md, u);
		} else {
			throw new IllegalArgumentException("role not found");
		}
	}

}
