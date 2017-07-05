package it.unifi.ing.swam.bean;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.swam.model.User;

@ConversationScoped
@Named
public class ConversationBean implements Serializable {

	private static final long serialVersionUID = 2L;

	@Inject
	private Conversation conversation;
	
	private User customer;

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
	
	public void setCustomer(User c){
		this.customer = c;
	}
	
	

}
