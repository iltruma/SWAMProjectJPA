package it.unifi.ing.swam.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import it.unifi.ing.swam.bean.ConversationMissionBean;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Waybill;

@ViewScoped
public class WaybillsToAssignPageController extends BasicController {

    @Inject
    private ConversationMissionBean conversationBean;

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

        waybills = new ArrayList<>();
        waybills = waybillDao.findUnassignedToDriver(userSession.getUser().getAgency());

        remainWeight = conversationBean.getTruckWeight() - conversationBean.getTotalWeight();
        remainVolume = conversationBean.getTruckVolume() - conversationBean.getTotalVolume();
    }

    public void addWaybills(List<Waybill> ws) {
        for (Waybill w : ws) {
            if (w.getWeight() < remainWeight && w.getVolume() < remainVolume) {
                conversationBean.getMission().addWaybill(w);
                remainWeight = conversationBean.getTruckWeight() - conversationBean.getTotalWeight();
                remainVolume = conversationBean.getTruckVolume() - conversationBean.getTotalVolume();
            } else {
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
