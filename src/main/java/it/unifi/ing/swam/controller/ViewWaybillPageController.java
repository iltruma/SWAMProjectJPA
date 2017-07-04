package it.unifi.ing.swam.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.RoleType;
import it.unifi.ing.swam.model.Waybill;

@Model
public class ViewWaybillPageController {
	
	@Inject
	private WaybillDao waybillDao;
	
	@Inject
	private UserSessionBean userSession;
	
	@Inject @HttpParam("id")
	private String waybillId;
	
	private Waybill waybill;
	
	public Waybill getWaybill(){
		if (waybill == null){
			initWaybill();
		}
		return waybill;
	}
	
	protected void initWaybill() {
		if(StringUtils.isEmpty(waybillId)) {
			throw new IllegalArgumentException("id not found");
		}
		try {
			Long id = Long.valueOf(waybillId);
			waybill = waybillDao.findById(id);
			
			if(userSession.getUser().hasRole(RoleType.CUSTOMER)){
				
			}
			
			
			if(!note.getUser().getId().equals( userSession.getUserId())) {
				throw new IllegalStateException("you are not the owner of the note");
			}
			
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("id not a number");
		}
	}
	
	
	
	
	
	
}
