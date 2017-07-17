package it.unifi.ing.swam.controller;

import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.dao.FareDao;
import it.unifi.ing.swam.model.Fare;
import it.unifi.ing.swam.model.ModelFactory;

@Named
@ViewScoped
public class EditFarePageController extends BasicController {

    private static final long serialVersionUID = 8L;

    @Inject
    private FareDao fareDao;

    @Inject
    private CustomerBean customerBean;

    @Inject
    @HttpParam("fare_id")
    private String fareId;

    @Inject
    @HttpParam("add")
    private String addFlag;

    private Fare fare;

    @PostConstruct
    protected void initEditFarePage() {

        if (!userSession.getUser().isOperator())
            throw new IllegalArgumentException("you cant view this page");
        if (StringUtils.isEmpty(fareId) && !Boolean.valueOf(addFlag))
            throw new IllegalArgumentException("fare id is empty");
        else if (Boolean.valueOf(addFlag)) {
            fare = ModelFactory.generateFare();
            customerBean.getCustomer().getCustomerRole().addFare(fare);

        } else
            fare = getFareFromId(Long.valueOf(fareId));

        if (fare == null)
            throw new IllegalArgumentException("fare not found");

        if (!customerBean.getCustomer().getCustomerRole().getFares().contains(fare))
            throw new IllegalArgumentException("fare not found for this customer");

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
     

    public Fare getFare() {
        return fare;
    }

    @Transactional
    public String save() {
        fareDao.save(fare);
        return "fare-view?fare_id=" + fare.getId() + "&faces-redirect=true";
    }

}
