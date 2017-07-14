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
    private CustomerBean conversationBean;

    @PostConstruct
    protected void initCustomerPage(){
        if(!userSession.getUser().isOperator())
            throw new IllegalArgumentException("you cant view this page");
        if(conversationBean.getCustomer() == null)
            throw new IllegalArgumentException("customer not found");
    }

    public User getCustomer() {
        return conversationBean.getCustomer();
    }

    public void exit(){
        conversationBean.endConversation();
    }

}
