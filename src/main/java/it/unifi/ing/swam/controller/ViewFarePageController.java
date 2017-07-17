package it.unifi.ing.swam.controller;

import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.FareDao;
import it.unifi.ing.swam.model.Fare;

@Named
@ViewScoped
public class ViewFarePageController extends BasicController {

    private static final long serialVersionUID = 13L;

    @Inject
    private CustomerBean customerBean;

    @Inject
    private FareDao fareDao;

    @Inject
    @HttpParam("fare_id")
    private String fareId;

    private Fare fare;

    @PostConstruct
    protected void initFarePage() {
        if (!userSession.getUser().isOperator())
            throw new IllegalArgumentException("you cant view this page");
        if (StringUtils.isEmpty(fareId))
            throw new IllegalArgumentException("fare id is empty");

        fare = getFareFromId(Long.valueOf(fareId));

        if (fare == null)
            throw new IllegalArgumentException("fare not found");

        if (!customerBean.getCustomer().getCustomerRole().getFares().contains(fare))
            throw new IllegalArgumentException("fare not found for this customer");
    }

    public Fare getFare() {
        return fare;
    }
    
    public Fare getFareFromId(Long id){
        //f = fareDao.findById(Long.valueOf(fareId));
    	Iterator<Fare> it = customerBean.getCustomer().getCustomerRole().getFares().iterator();
    	Fare f = null;
    	while(it.hasNext() && f == null){
    		f = it.next();
    		if(!f.getId().equals(id)){
    			f = null;
    		}
    	}
		return f;
    }

    public String edit() {
        return "fare-edit?fare_id=" + fare.getId() + "&faces-redirect=true";
    }

}
