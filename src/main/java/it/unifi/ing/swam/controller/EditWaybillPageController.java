package it.unifi.ing.swam.controller;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.swam.bean.producer.HttpParam;
import it.unifi.ing.swam.controller.strategy.RoleStrategy;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Waybill;

@ViewScoped
public class EditWaybillPageController extends BasicController {

	@Inject
	private WaybillDao waybillDao;
	
	@Inject
	private MissionDao missionDao;

	@Inject
	@HttpParam("id")
	private String waybillId;

	@Inject
	@HttpParam("add")
	private String addFlag;

	private Waybill waybill;

	@Transactional
	public String save() {
		waybillDao.save(waybill);
		;
		return "ViewPage" + waybillId + roleId;
	}

	@PostConstruct
	protected void initStrategy() {
		
		if(StringUtils.isEmpty(waybillId) && !Boolean.valueOf(addFlag)) {
			throw new IllegalArgumentException("id not found");
		} else if (Boolean.valueOf(addFlag)){
			waybillId = null;			
		}

		RoleStrategy strategy = RoleStrategy.getStrategyFrom(currentRole, waybillId, waybillDao, missionDao, userSession.getUser());
		strategy.initWaybill();

	}

}
