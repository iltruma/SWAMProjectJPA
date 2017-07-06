package it.unifi.ing.swam.controller;

import java.util.Calendar;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.WaybillDao;
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
        if (userSession.getUser().isCustomer())
            return waybillDao.findProposedBySender(userSession.getUser());
        else
            return null;
    }

    public List<Waybill> getValidatedWaybillCustomer() {
        if (userSession.getUser().isCustomer())
            return waybillDao.findValidatedBySender(userSession.getUser());
        else
            return null;
    }

    public List<Waybill> getProposedWaybillOperator() {
        if (userSession.getUser().isOperator())
            return waybillDao.findProposedByAgency(userSession.getUser().getAgency());
        else
            return null;
    }

    public List<Waybill> getUnassignedToDriver() {
        if (userSession.getUser().isOperator())
            return waybillDao.findUnassignedToDriver(userSession.getUser().getAgency());
        else
            return null;
    }

    public List<Waybill> getTodayMission() {
        if (userSession.getUser().isDriver())
            return missionDao.findByDriverAndDate(userSession.getUser(), Calendar.getInstance()).getWaybills();
        else
            return null;
    }

}
