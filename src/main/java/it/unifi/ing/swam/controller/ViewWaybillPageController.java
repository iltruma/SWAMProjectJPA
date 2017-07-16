package it.unifi.ing.swam.controller;


import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.controller.strategy.RoleStrategy;
import it.unifi.ing.swam.dao.AgencyDao;
import it.unifi.ing.swam.dao.ItemDao;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.RoleDao;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Waybill;

@Model
public class ViewWaybillPageController extends BasicController {

    private static final long serialVersionUID = 13L;

    @Inject
    private RoleDao roleDao;

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

    @Inject @HttpParam("id")
    private String waybillId;

    private RoleStrategy strategy;


    public RoleStrategy getStrategy() {
        return strategy;
    }

    protected void initStrategy(){
        if(StringUtils.isEmpty(roleId))
            throw new IllegalArgumentException("role id not found");

        if(StringUtils.isEmpty(waybillId))
            throw new IllegalArgumentException("waybill id not found");

        currentRole = roleDao.findById(Long.valueOf(roleId));
        strategy = RoleStrategy.getStrategyFrom(currentRole, waybillId, userSession.getUser());
        strategy.setDaos(waybillDao, agencyDao, itemDao, userDao, missionDao);
    }

    public Waybill getWaybill() {
        return strategy.getWaybill();
    }

    @PostConstruct
    protected void initWaybill() {
        if(strategy==null)
            initStrategy();
        strategy.initWaybill();

    }

}
