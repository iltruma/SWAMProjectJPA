package it.unifi.ing.swam.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@Named
@ViewScoped
public class SearchPageController extends BasicController {

	private static final long serialVersionUID = 11L;

	@Inject
	private WaybillDao waybillDao;

	@Inject
	private UserDao userDao;

	private List<Waybill> results;

	private Waybill waybillQuery;

	@PostConstruct
	protected void initSearchPage() {
		if (!(userSession.getUser().isCustomer() || userSession.getUser().isOperator()))
			throw new IllegalArgumentException("you cant view this page");

		if (StringUtils.isEmpty(roleId))
			throw new IllegalArgumentException("role id not found");
		currentRole = roleDao.findById(Long.valueOf(roleId));

		results = new ArrayList<>();
		waybillQuery = ModelFactory.generateWaybill();
		waybillQuery.setTracking(null);

		User operator = ModelFactory.generateUser();
		operator.addRole(ModelFactory.generateOperator());
		waybillQuery.setOperator(operator);

		User customer = ModelFactory.generateUser();
		customer.addRole(ModelFactory.generateCustomer());
		waybillQuery.setSender(customer);
	}

	public void search(int max) {
		results = waybillDao.advancedSearch(waybillQuery, max);
	}

	public void setOperator(Long id) {
		if (id == null)
			return;
		User u = userDao.findById(id);
		if (!u.isOperator())
			throw new IllegalArgumentException("operator id not found");
		waybillQuery.setOperator(u);
	}

	public Long getOperator(){
		return waybillQuery.getOperator().getId();
	}

	public void setSender(Long id) {
		if (id == null)
			return;
		User u = userDao.findById(id);
		if (!u.isCustomer())
			throw new IllegalArgumentException("sender id not found");
		waybillQuery.setSender(u);
	}

	public Long getSender(){
		return waybillQuery.getSender().getId();
	}

	public void setTracking(Tracking t){

		waybillQuery.setTracking(t);
	}

	public Tracking getTracking(){
		return waybillQuery.getTracking();
	}

	public Waybill getWaybillQuery() {
		return waybillQuery;
	}

	public List<Waybill> getResults() {
		return results;
	}

	public void setResults(List<Waybill> results) {
		this.results = results;
	}

}
