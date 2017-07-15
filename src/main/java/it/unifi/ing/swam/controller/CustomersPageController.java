package it.unifi.ing.swam.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.dao.CustomerDao;
import it.unifi.ing.swam.model.Customer;
import it.unifi.ing.swam.model.User;

@Named
@ViewScoped
public class CustomersPageController extends BasicController {

    private static final long serialVersionUID = 6L;

    @Inject
    private CustomerDao customerDao;

    @Inject
    private CustomerBean conversationBean;

    private List<User> customers;


    @PostConstruct
    protected void initCustomersPage(){
        if(!userSession.getUser().isOperator())
            throw new IllegalArgumentException("you cant view this page");
        List<Customer> results = customerDao.findByOperator(userSession.getUser());
        customers = new ArrayList<>();

        for(Customer c: results){
            customers.add(c.getOwner());
        }

    }

    public List<User> getCustomers() {
        return customers;
    }

    public String selectCustomer(User u){
        conversationBean.initConversation();
        conversationBean.setCustomer(u);
        return "customer-view?faces-redirect=true";
    }

    public String createCustomer(){
        conversationBean.initConversation();
        return "customer-edit?faces-redirect=true";
    }


}
