package it.unifi.ing.swam.controller;

import javax.inject.Inject;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.RoleDao;
import it.unifi.ing.swam.model.Role;

public class BasicController {
	
	@Inject
	protected UserSessionBean userSession;
	
	@Inject @HttpParam("roleId")
	protected String roleId; 
	
	@Inject
	protected RoleDao roleDao;
	
	protected Role currentRole;

}
