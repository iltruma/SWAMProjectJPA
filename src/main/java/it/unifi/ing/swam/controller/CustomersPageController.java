package it.unifi.ing.swam.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.dao.CustomerDao;
import it.unifi.ing.swam.model.Customer;
import it.unifi.ing.swam.model.User;

@ViewScoped
public class CustomersPageController extends BasicController{

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

    public void selectCustomer(User u){
        conversationBean.initConversation();
        conversationBean.setCustomer(u);
    }

    public void createCustomer(){
        conversationBean.initConversation();
    }


}
