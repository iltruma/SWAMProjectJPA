package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.controller.strategy.RoleStrategy;
import it.unifi.ing.swam.dao.AgencyDao;
import it.unifi.ing.swam.dao.ItemDao;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Waybill;

@Model
public class EditWaybillPageController extends BasicController {

    private static final long serialVersionUID = 9L;

    @Inject
    @HttpParam("id")
    private String waybillId;

    @Inject
    @HttpParam("add")
    private String addFlag;
    
    @Inject
    protected WaybillDao waybillDao;

    @Inject
    protected AgencyDao agencyDao;

    @Inject
    protected ItemDao itemDao;

    @Inject
    protected UserDao userDao;
  
    @Inject
    protected MissionDao missionDao;

    private RoleStrategy strategy;

    @PostConstruct
    protected void initWaybill() {
        if(strategy == null) {
            initStrategy();
        }
        strategy.initWaybill();
        strategy.checkEdit();
        strategy.getWaybill();
    }

    protected void initStrategy() {
        if(StringUtils.isEmpty(roleId))
            throw new IllegalArgumentException("role id not found");

        if(StringUtils.isEmpty(waybillId) && !Boolean.valueOf(addFlag))
            throw new IllegalArgumentException("id not found");
        else if (Boolean.valueOf(addFlag)){
            waybillId = null;
        }
        currentRole = roleDao.findById(Long.valueOf(roleId));

        strategy = RoleStrategy.getStrategyFrom(currentRole, waybillId, userSession.getUser());
        strategy.setDaos(waybillDao, agencyDao, itemDao, userDao, missionDao);
    }

    public Waybill getWaybill() {
        return strategy.getWaybill();
    }

    public RoleStrategy getStrategy() {
        return strategy;
    }
    
    @Transactional
    public void save(){
    	this.strategy.save();
    }
    
    @Transactional
    public String saveSender() {
        userDao.save(getWaybill().getSender());
        return "ViewPage" + getWaybill().getId() + userSession.getUser().getCustomerRole().getId();
    }
    


}
