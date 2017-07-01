package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;

    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy="owner")
    private List<Role> roles;

    @ManyToOne
    private Agency agency;


    public User() {

    }

    public User(String uuid) {
        super(uuid);
        this.roles = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void addRole(Role r) {
        this.roles.add(r);
    	r.setOwner(this);
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Boolean hasRole(RoleType t){
    	Iterator<Role> roleIterator = this.getRoles().iterator();
    	Boolean hasType = false;
    	while(roleIterator.hasNext() && !hasType){
    		hasType = roleIterator.next().getType().equals(t);
    	}
    	return hasType;
    }
    
    // Can only be called by a driver
    public Role getDriverRole() throws UnsupportedOperationException {
    	Iterator<Role> roleIterator = this.getRoles().iterator();
    	Role driver = null;
    	while(roleIterator.hasNext() && driver != null){
    		Role role = roleIterator.next();
    		 if(role.getType().equals(RoleType.DRIVER))
    			 driver = role;
    	}
    	if(driver == null) 
    		throw new UnsupportedOperationException("The User was not a Driver");
    	return driver;
       
    }
    
    // Can only be called by a Customer
    public Role getCustomerRole() throws UnsupportedOperationException{
    	Iterator<Role> roleIterator = this.getRoles().iterator();
    	Role customer = null;
    	while(roleIterator.hasNext() && customer != null){
    		Role role = roleIterator.next();
    		 if(role.getType().equals(RoleType.CUSTOMER))
    			 customer = role;
    	}
    	if(customer == null) 
    		throw new UnsupportedOperationException("The User was not a Customer");
    	return customer;
       
    }
    
    // Can only be called by an Operator
    public Role getOperatorRole() throws UnsupportedOperationException{
    	Iterator<Role> roleIterator = this.getRoles().iterator();
    	Role operator = null;
    	while(roleIterator.hasNext() && operator != null){
    		Role role = roleIterator.next();
    		 if(role.getType().equals(RoleType.OPERATOR))
    			 operator = role;
    	}
    	if(operator == null) 
    		throw new UnsupportedOperationException("The User was not an Operator");
    	return operator;
       
    }


}
