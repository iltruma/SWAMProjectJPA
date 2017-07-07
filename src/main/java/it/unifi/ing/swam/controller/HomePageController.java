package it.unifi.ing.swam.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@Model
public class HomePageController {

    @Inject
    private WaybillDao waybillDao;
    @Inject
    private MissionDao missionDao;

    @Inject
    private UserSessionBean userSession;

    public void checkIsLogged() {
        if (!userSession.isLoggedIn())
            throw new RuntimeException("No User logged in!");
    }

    public List<Waybill> getProposedWaybillCustomer() {
        if (getUser().isCustomer())
            return waybillDao.findProposedBySender(getUser());
        else
            return null;
    }

    public List<Waybill> getValidatedWaybillCustomer() {
        if (getUser().isCustomer())
            return waybillDao.findValidatedBySender(getUser());
        else
            return null;
    }

    public List<Waybill> getProposedWaybillOperator() {
        if (getUser().isOperator())
            return waybillDao.findProposedByAgency(getUser().getAgency());
        else
            return null;
    }

    public List<Waybill> getUnassignedToDriver() {
        if (getUser().isOperator())
            return waybillDao.findUnassignedToDriver(getUser().getAgency());
        else
            return null;
    }

    public List<Waybill> getTodayMission() {
        if (getUser().isDriver()){
        	Mission m = missionDao.findByDriverAndDate(getUser(), Calendar.getInstance());
        if (m == null)
        	return new ArrayList<Waybill>();
        return m.getWaybills();
        }
        else
            return null;
    }
    
    public User getUser() {
    	return userSession.getUser();
    }

}
