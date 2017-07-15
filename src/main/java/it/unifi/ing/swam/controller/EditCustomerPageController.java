package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import it.unifi.ing.swam.bean.CustomerBean;
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
	private CustomerBean conversationBean;

	@PostConstruct
	protected void initEditCustomerPage() {

		if (!userSession.getUser().isOperator())
			throw new IllegalArgumentException("you cant view this page");

		if (conversationBean.getCustomer() == null) {
			conversationBean.setCustomer(ModelFactory.generateUser());
			conversationBean.getCustomer().addRole(ModelFactory.generateCustomer());
			conversationBean.getCustomer().setAgency(userSession.getUser().getAgency());
			conversationBean.getCustomer().getCustomerRole().setOperator(userSession.getUser());
		}

		if (!conversationBean.getCustomer().getAgency().equals(userSession.getUser().getAgency()))
			throw new IllegalStateException("customer not Editable");

	}

	public User getCustomer() {
		return conversationBean.getCustomer();
	}

	@Transactional
	public String save() {
		userDao.save(conversationBean.getCustomer());

		return "customer-view?faces-redirect=true";
	}

	public String viewFare(Fare f) {
		return "fare-view?fare_id=" + f.getId() + "&faces-redirect=true";
	}

	public String createFare() {
		return "fare-edit?add=true&faces-redirect=true";
	}

	public void blockCustomer() {
		conversationBean.getCustomer().getCustomerRole().setState(State.BLOCKED);
	}

	public void unlockCustomer() {
		conversationBean.getCustomer().getCustomerRole().setState(State.ACTIVE);
	}

}
