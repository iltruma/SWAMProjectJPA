package it.unifi.ing.swam.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.dao.FareDao;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.model.Fare;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.State;
import it.unifi.ing.swam.model.User;

@Named
@ViewScoped
public class EditCustomerPageController extends BasicController {

    private static final long serialVersionUID = 7L;

    @Inject
    private UserDao userDao;

    @Inject
    private CustomerBean customerBean;

    @Inject
    private FareDao fareDao;

    @PostConstruct
    protected void initEditCustomerPage() {

        if (!userSession.getUser().isOperator())
            throw new IllegalArgumentException("you cant view this page");

        if (customerBean.getCustomer() == null) {
            customerBean.setCustomer(ModelFactory.generateUser());
            customerBean.getCustomer().addRole(ModelFactory.generateCustomer());
            customerBean.getCustomer().setAgency(userSession.getUser().getAgency());
            customerBean.getCustomer().getCustomerRole().setOperator(userSession.getUser());
        }

        if (!customerBean.getCustomer().getAgency().equals(userSession.getUser().getAgency()))
            throw new IllegalStateException("customer not Editable");
    }

    public User getCustomer() {
        return customerBean.getCustomer();
    }

    @Transactional
    public String save() {
        userDao.save(customerBean.getCustomer());
        return "customer-view?faces-redirect=true";
    }

    public String viewFare(Fare f) {
        return "fare-view?fare_id=" + f.getId() + "&faces-redirect=true";
    }

    public String createFare() {
        return "fare-edit?add=true&faces-redirect=true";
    }

    @Transactional
    public void removeFare(Fare f) {
        customerBean.getCustomer().getCustomerRole().getFares().remove(f);
    }

    public void blockCustomer() {
        customerBean.getCustomer().getCustomerRole().setState(State.BLOCKED);
    }

    public void unlockCustomer() {
        customerBean.getCustomer().getCustomerRole().setState(State.ACTIVE);
    }

    @Transactional
    public String back() {
    	List<Fare> removeList = new ArrayList<>();
    	for(Fare f : customerBean.getCustomer().getCustomerRole().getFares()){
    		if(fareDao.isUnassignedToCustomer(f)){
    			removeList.add(f);
    			fareDao.delete(f);
    		}
    	}
		customerBean.getCustomer().getCustomerRole().getFares().removeAll(removeList);

		if(customerBean.getCustomer().getId() != null){
	        return "customer-view?faces-redirect=true";
		}
        return "customers?faces-redirect=true";
    }

    public void setPassword (String password) {
        if(password.equals(""))
            return;
        customerBean.getCustomer().setPassword(password);
    }

    public String getPassword () {
        return "";
    }
}
