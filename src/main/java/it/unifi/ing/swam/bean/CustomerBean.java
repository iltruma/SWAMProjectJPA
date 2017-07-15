package it.unifi.ing.swam.bean;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.swam.model.User;


@Named
@ConversationScoped
public class CustomerBean implements Serializable {

	private static final long serialVersionUID = 3181632097971721800L;

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
        customer = c;
    }

    public User getCustomer(){
        return customer;
    }


}
