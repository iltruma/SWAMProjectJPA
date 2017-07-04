package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

	@OneToMany(cascade = { CascadeType.PERSIST }, mappedBy = "owner")
	private List<Role> roles;

	@ManyToOne
	private Agency agency;

	protected User() {

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

	protected Boolean hasRole(RoleType t) {
		Iterator<Role> roleIterator = this.getRoles().iterator();
		Boolean hasType = false;
		while (roleIterator.hasNext() && !hasType) {
			hasType = roleIterator.next().getType().equals(t);
		}
		return hasType;
	}
	
	public Boolean isCustomer(){
		return this.hasRole(RoleType.CUSTOMER);
	}
	
	public Boolean isOperator(){
		return this.hasRole(RoleType.OPERATOR);
	}
	
	public Boolean isDriver(){
		return this.hasRole(RoleType.DRIVER);
	}

	public void addRole(Role r) throws IllegalArgumentException {
		if (this.hasRole(r.getType()))
			throw new IllegalArgumentException("the user already contains the role " + r.getType().toString());
		this.roles.add(r);
		r.setOwner(this);
	}

	public void removeRole(Role r) throws IllegalArgumentException {
		try {
			roles.remove(this.getRoleFromRoleType(r.getType()));
		} catch(UnsupportedOperationException e) {
			throw new IllegalArgumentException("the user doesnt have the role " + r.getType().toString());
		}

	}

	// Can only be called by a Driver
	public Role getDriverRole() throws UnsupportedOperationException {
		return this.getRoleFromRoleType(RoleType.DRIVER);
	}

	// Can only be called by a Customer
	public Role getCustomerRole() throws UnsupportedOperationException {
		return this.getRoleFromRoleType(RoleType.CUSTOMER);
	}

	// Can only be called by an Operator
	public Role getOperatorRole() throws UnsupportedOperationException {
		return this.getRoleFromRoleType(RoleType.OPERATOR);
	}

	// Can only be called by an User who as the RoleType passed as argument
	private Role getRoleFromRoleType(RoleType roleType) {
		Iterator<Role> roleIterator = this.getRoles().iterator();
		Role role = null;
		while (roleIterator.hasNext() && role == null) {
			role = roleIterator.next();
			if (!role.getType().equals(roleType))
				role = null;
		}
		if(role == null)
			throw new UnsupportedOperationException("The User was not a " + roleType.toString() );
		return role;

	}

}
