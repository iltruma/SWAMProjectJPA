package it.unifi.ing.swam.controller.strategy;



import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

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
    
    
    public void setDaos(WaybillDao wd, AgencyDao ad, ItemDao id, UserDao ud, MissionDao md){
    	this.waybillDao = wd;
    	this.agencyDao = ad;
    	this.itemDao = id;
    	this.userDao = ud;
    	this.missionDao = md;
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

    public void setCustomer(Long id){
        throw new UnsupportedOperationException();
    }

    public void setAgency(Long id){
        throw new UnsupportedOperationException();
    }

    public void addItem(Long id){
        throw new UnsupportedOperationException();
    }

    public void checkEdit() {
        throw new UnsupportedOperationException();
    }

    public void setSignAndTracking() {
        throw new UnsupportedOperationException();
    }

    @Transactional
    public String save() {
        waybillDao.save(waybill);
        return "ViewPage" + waybill.getId() + user.getCustomerRole().getId();
    }

    public Waybill getWaybill() {
        return waybill;
    }

}
