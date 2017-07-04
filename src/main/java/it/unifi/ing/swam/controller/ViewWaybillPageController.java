package it.unifi.ing.swam.controller;


import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.controller.strategy.RoleStrategy;
import it.unifi.ing.swam.dao.RoleDao;
import it.unifi.ing.swam.model.Waybill;

@Model
public class ViewWaybillPageController extends BasicController{

	@Inject
	private RoleDao roleDao;

	@Inject @HttpParam("id")
	private String waybillId;

	private Waybill waybill;

	@PostConstruct
	protected void initWaybill() {

		if(StringUtils.isEmpty(roleId)) {
			throw new IllegalArgumentException("role id not found");
		}
		
		if(StringUtils.isEmpty(waybillId)) {
			throw new IllegalArgumentException("waybill id not found");
		}
		
		currentRole = roleDao.findById(Long.valueOf(roleId));

		RoleStrategy strategy = RoleStrategy.getStrategyFrom(currentRole, waybillId, userSession.getUser());
		waybill = strategy.initWaybill();
	
	}

	public Waybill getWaybill() {
		return waybill;
	}
	
	






}
