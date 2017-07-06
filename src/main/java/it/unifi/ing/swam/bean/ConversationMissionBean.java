package it.unifi.ing.swam.bean;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.Waybill;

@ConversationScoped
@Named
public class ConversationMissionBean implements Serializable {

	private static final long serialVersionUID = 3L;

	@Inject
	private Conversation conversation;

	private Mission mission;

	private Float truckWeight;
	private Float truckVolume;
	
	public void initConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}
	
	public String endConversation(){
	    if(!conversation.isTransient()){
	      conversation.end();
	    }
	    return "back";
	  }
	
	public Mission getMission(){
		return mission;
	}
	
	public void setMission(Mission m){
		this.mission = m;
	}
	
	public Float getTruckWeight() {
		return truckWeight;
	}

	public void setTruckWeight(Float trackWeight) {
		this.truckWeight = trackWeight;
	}

	public Float getTruckVolume() {
		return truckVolume;
	}

	public void setTruckVolume(Float trackVolume) {
		this.truckVolume = trackVolume;
	}
	
	public Float getTotalWeight(){
		Float missionTotalWeight = 0F;
		for(Waybill w : mission.getWaybills()){
			missionTotalWeight += w.getLoad().getTotalWeight();
		}
		return missionTotalWeight;
	}
	
	public Float getTotalVolume(){
		Float missionTotalVolume = 0F;
		for(Waybill w : mission.getWaybills()){
			missionTotalVolume += w.getLoad().getTotalWeight();
		}
		return missionTotalVolume;
	}

}
