package it.unifi.ing.swam.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.ConversationBean;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Waybill;

@ViewScoped
public class WaybillsToAssignPageController extends BasicController {

	@Inject
	private ConversationBean conversationBean;

	@Inject
	private WaybillDao waybillDao;

	private List<Waybill> waybills;
	
	private Float remainWeight;
	private Float remainVolume; 

	@PostConstruct
	protected void initAssignDriverPage() {
		if (!userSession.getUser().isOperator()) {
			throw new IllegalArgumentException("you cant view this page");
		}

		List<Waybill> tempWaybills = waybillDao.findUnassignedToDriver(userSession.getUser().getAgency());
		waybills = new ArrayList<Waybill>();
		remainWeight = conversationBean.getTruckWeight() - conversationBean.getTotalWeight();
		remainVolume = conversationBean.getTruckVolume() - conversationBean.getTotalVolume();

		for (Waybill w : tempWaybills) {
			if (w.getWeight() < remainWeight && w.getVolume() < remainVolume) {
				waybills.add(w);
			}
		}
	}

	public void addWaybills(List<Waybill> ws) {
		for (Waybill w : ws) {
			if (w.getWeight() < remainWeight && w.getVolume() < remainVolume) {
				conversationBean.getMission().addWaybill(w);
			}
			else {
				throw new IllegalArgumentException("you can't add this waybill " + w.getId().toString());
			}
		}
	}

	public List<Waybill> getWaybills() {
		return waybills;
	}

	public Float getRemainWeight() {
		return remainWeight;
	}

	public Float getRemainVolume() {
		return remainVolume;
	}

}
