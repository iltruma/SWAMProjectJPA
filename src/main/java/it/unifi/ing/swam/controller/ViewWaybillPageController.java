package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.controller.strategy.RoleStrategy;
import it.unifi.ing.swam.model.Waybill;

@Named
@ViewScoped
public class ViewWaybillPageController extends BasicController {

	private static final long serialVersionUID = 13L;


	@Inject Instance<RoleStrategy> roleStrategyInstance;

	@Inject
	@HttpParam("id")
	private String waybillId;

	private RoleStrategy strategy;

	public RoleStrategy getStrategy() {
		return strategy;
	}

	protected void initStrategy() {
		if (StringUtils.isEmpty(roleId))
			throw new IllegalArgumentException("role id not found");

		if (StringUtils.isEmpty(waybillId))
			throw new IllegalArgumentException("waybill id not found");

		currentRole = roleDao.findById(Long.valueOf(roleId));
		strategy = RoleStrategy.getStrategy(roleStrategyInstance, currentRole, userSession.getUser(), waybillId);
	}

	public Waybill getWaybill() {
		return strategy.getWaybill();
	}

	@PostConstruct
	protected void initWaybill(){
		if (strategy == null)
			initStrategy();
		strategy.initWaybill();
	}

}
