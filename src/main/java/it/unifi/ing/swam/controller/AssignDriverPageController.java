package it.unifi.ing.swam.controller;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.swam.bean.MissionBean;
import it.unifi.ing.swam.dao.DriverDao;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Driver;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Waybill;

@Named
@ViewScoped
public class AssignDriverPageController extends BasicController {

    private static final long serialVersionUID = 5L;

    @Inject
    private WaybillDao waybillDao;

    @Inject
    private DriverDao driverDao;

    @Inject
    private MissionDao missionDao;

    @Inject
    private MissionBean conversationBean;

    private List<Driver> driversAvailable;

    private List<Waybill> waybills;

    @PostConstruct
    protected void initAssignDriverPage(){
        if(!userSession.getUser().isOperator())
            throw new IllegalArgumentException("you cant view this page");

        waybills = waybillDao.findUnassignedToDriver(userSession.getUser().getAgency());
        driversAvailable = driverDao.findAvailable(userSession.getUser().getAgency());

    }

    public List<Driver> getDriversAvailable() {
        return driversAvailable;
    }

    public List<Waybill> getWaybills() {
        return waybills;
    }

    public void selectDriver(Driver d){
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);

        Mission m = missionDao.findByDriverAndDate(d.getOwner(), tomorrow);
        if (m == null){
            m = ModelFactory.generateMission();
            m.setDriver(d);
            m.setDate(tomorrow);
        }

        conversationBean.initConversation();
        conversationBean.setMission(m);
    }

}
