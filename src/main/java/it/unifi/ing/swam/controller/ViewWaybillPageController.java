package it.unifi.ing.swam.controller;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.RoleDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@Model
public class ViewWaybillPageController extends BasicController{

	@Inject
	private WaybillDao waybillDao;

	@Inject
	private RoleDao roleDao;

	@Inject
	private MissionDao missionDao;

	@Inject @HttpParam("id")
	private String waybillId;


	private Waybill waybill;

	@PostConstruct
	protected void initWaybill() {

		if(StringUtils.isEmpty(waybillId)) {
			throw new IllegalArgumentException("waybill id not found");
		}
		if(StringUtils.isEmpty(roleId)) {
			throw new IllegalArgumentException("role id not found");
		}
		try {
			Long id = Long.valueOf(waybillId);
			waybill = waybillDao.findById(id);
			User user = userSession.getUser();
			currentRole = roleDao.findById(Long.valueOf(roleId));

			if(user.isCustomer() && user.getCustomerRole().equals(currentRole) && !waybill.getSender().equals(user)){
				throw new IllegalStateException("you can't view this waybill");
			}

			else if(user.isOperator() && user.getOperatorRole().equals(currentRole) && !waybill.getOperator().equals(user)){
				throw new IllegalStateException("you can't view this waybill");
			}

			else if(user.isDriver() && user.getOperatorRole().equals(currentRole) && !missionDao.findByDriverAndDate(user, Calendar.getInstance()).getWaybills().contains(waybill)){
				throw new IllegalStateException("you can't view this waybill");
			}


		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("waybill id not a number");
		}
	}






}
