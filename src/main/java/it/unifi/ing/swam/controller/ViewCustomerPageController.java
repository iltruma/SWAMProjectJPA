package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.model.User;

@Model
public class ViewCustomerPageController extends BasicController {

    private static final long serialVersionUID = 12L;

    @Inject
    private CustomerBean customerBean;

    @PostConstruct
    protected void initCustomerPage(){
        if(!userSession.getUser().isOperator())
            throw new IllegalArgumentException("you cant view this page");
        if(customerBean.getCustomer() == null)
            throw new IllegalArgumentException("customer not found");
    }

    public User getCustomer() {
        return customerBean.getCustomer();
    }
    
    public String exit(){
        customerBean.endConversation();
        return "customers?faces-redirect=true";

    }
    
    public String edit(){
        return "customer-edit?faces-redirect=true";
    }

}
