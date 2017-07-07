package it.unifi.ing.swam.controller;

import java.io.Serializable;

import javax.inject.Inject;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.RoleDao;
import it.unifi.ing.swam.model.Role;

public class BasicController implements Serializable{

	private static final long serialVersionUID = 4L;

	@Inject
    protected UserSessionBean userSession;

    @Inject @HttpParam("roleId")
    protected String roleId;

    @Inject
    protected RoleDao roleDao;

    protected Role currentRole;

    public Role getCurrentRole() {
        return currentRole;
    }


}
