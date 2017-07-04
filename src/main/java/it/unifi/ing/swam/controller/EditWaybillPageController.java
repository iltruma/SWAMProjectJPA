package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.controller.strategy.RoleStrategy;
import it.unifi.ing.swam.model.Waybill;

@ViewScoped
public class EditWaybillPageController extends BasicController {
	
	@Inject
	@HttpParam("id")
	private String waybillId;

	@Inject
	@HttpParam("add")
	private String addFlag;

	private Waybill waybill;
	private RoleStrategy strategy;

	@PostConstruct
	protected void initStrategy() {
		if(StringUtils.isEmpty(roleId)) {
			throw new IllegalArgumentException("role id not found");
		}
		
		if(StringUtils.isEmpty(waybillId) && !Boolean.valueOf(addFlag)) {
			throw new IllegalArgumentException("id not found");
		} else if (Boolean.valueOf(addFlag)){
			waybillId = null;			
		}
		
		currentRole = roleDao.findById(Long.valueOf(roleId));

		strategy = RoleStrategy.getStrategyFrom(currentRole, waybillId, userSession.getUser());
		strategy.initWaybill();

	}

	public Waybill getWaybill() {
		return waybill;
	}
	
	

}
