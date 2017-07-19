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
public class EditWaybillPageController extends BasicController {

	private static final long serialVersionUID = 9L;

	@Inject
	@HttpParam("id")
	private String waybillId;

	@Inject
	@HttpParam("add")
	private String addFlag;

	@Inject Instance<RoleStrategy> roleStrategyInstance;

	private RoleStrategy strategy;

	public String getAddFlag() {
		return addFlag;
	}

	@PostConstruct
	protected void initWaybill() {
		if (strategy == null)
			initStrategy();
		strategy.initWaybill();
		strategy.checkEdit();
		strategy.getWaybill();
	}

	protected void initStrategy() {
		if (StringUtils.isEmpty(roleId))
			throw new IllegalArgumentException("role id not found");
		currentRole = roleDao.findById(Long.valueOf(roleId));

		if (StringUtils.isEmpty(waybillId) && !Boolean.valueOf(addFlag))
			throw new IllegalArgumentException("id not found");
		else if (Boolean.valueOf(addFlag)){
			waybillId = null;
			if(currentRole.isCustomer() && (userSession.getUser().getCustomerRole().getFares() == null || userSession.getUser().getCustomerRole().getFares().isEmpty())){
				throw new IllegalArgumentException("no fares for this customer!");
			}
		}
		strategy = RoleStrategy.getStrategy(roleStrategyInstance, currentRole, userSession.getUser(), waybillId);
	}

	public Waybill getWaybill() {
		return strategy.getWaybill();
	}

	public RoleStrategy getStrategy() {
		return strategy;
	}

	public String save() {
		strategy.save();
		return "waybill-view?id=" + getWaybill().getId() + "&roleId=" + currentRole.getId() + "&faces-redirect=true";
	}

	public String delete() {
		strategy.delete();
		return "home?&faces-redirect=true";
	}

	public String signAndSave() {
		strategy.setSignAndTracking();
		return save();
	}


}
