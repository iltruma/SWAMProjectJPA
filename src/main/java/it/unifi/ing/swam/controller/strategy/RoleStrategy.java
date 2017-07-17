package it.unifi.ing.swam.controller.strategy;

import javax.enterprise.context.Dependent;

import it.unifi.ing.swam.dao.AgencyDao;
import it.unifi.ing.swam.dao.ItemDao;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Role;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@Dependent
public abstract class RoleStrategy {

    protected String waybillId;

    protected WaybillDao waybillDao;

    protected AgencyDao agencyDao;

    protected ItemDao itemDao;

    protected UserDao userDao;

    protected MissionDao missionDao;

    protected User user;
    protected Waybill waybill;

    public void setDaos(WaybillDao wd, AgencyDao ad, ItemDao id, UserDao ud, MissionDao md) {
        waybillDao = wd;
        agencyDao = ad;
        itemDao = id;
        userDao = ud;
        missionDao = md;
    }

    protected RoleStrategy(String wid, User u) {
        waybillId = wid;
        user = u;
    }

    public abstract Waybill initWaybill();

    public static RoleStrategy getStrategyFrom(Role r, String wid, User u) {
        if (r.isCustomer())
            return new CustomerStrategy(wid, u);
        else if (r.isOperator())
            return new OperatorStrategy(wid, u);
        else if (r.isDriver())
            return new DriverStrategy(wid, u);
        else
            throw new IllegalArgumentException("role not found");
    }

    public Long getSender() {
        if (waybill.getSender() != null)
            return waybill.getSender().getId();
        else
            return null;
    }

    public Long getAgency() {
        if (waybill.getReceiver().getDestinationAgency() != null)
            return waybill.getReceiver().getDestinationAgency().getId();
        else
            return null;
    }

    public Long getNewItem() {
        return null;
    }

    public void setSender(Long id) {
        throw new UnsupportedOperationException();
    }

    public void setAgency(Long id) {
        throw new UnsupportedOperationException();
    }

    public void setNewItem(Long id) {
        throw new UnsupportedOperationException();
    }

    public void checkEdit() {
        throw new UnsupportedOperationException();
    }

    public void setSignAndTracking() {
        throw new UnsupportedOperationException();
    }

    public void save() {
        waybillDao.save(waybill);
    }

    public Waybill getWaybill() {
        return waybill;
    }

    public void delete() {
        throw new UnsupportedOperationException();
    }

}
