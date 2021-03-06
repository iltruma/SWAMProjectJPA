package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotNull
    @Column(unique = true)
    @Size(min = 3, max=30)
    private String username;

    @NotNull
    private String password;

    private byte[] salt;

    private String name;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    @Size(min = 1)
    @OrderBy("type")
    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "owner")
    private List<Role> roles;

    @NotNull
    @ManyToOne
    private Agency agency;

    protected User() {

    }

    public User(String uuid) {
        super(uuid);
        roles = new ArrayList<>();
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
        if (salt == null)
            throw new IllegalArgumentException("salt cannot be null");
        this.password = UserPasswordTools.encrypt(password, salt);
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
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

    protected Boolean hasRole(Role.Type t) {
        Iterator<Role> roleIterator = getRoles().iterator();
        Boolean hasType = false;
        while (roleIterator.hasNext() && !hasType)
            hasType = roleIterator.next().getType().equals(t);
        return hasType;
    }

    public Boolean isCustomer() {
        return hasRole(Role.Type.CUSTOMER);
    }

    public Boolean isOperator() {
        return hasRole(Role.Type.OPERATOR);
    }

    public Boolean isDriver() {
        return hasRole(Role.Type.DRIVER);
    }

    public void addRole(Role r) throws IllegalArgumentException {
        if (hasRole(r.getType()))
            throw new IllegalArgumentException("the user already contains the role " + r.getType().toString());
        roles.add(r);
        r.setOwner(this);
    }

    public void removeRole(Role r) throws IllegalArgumentException {
        try {
            roles.remove(getRoleFromRoleType(r.getType()));
        } catch (UnsupportedOperationException e) {
            throw new IllegalArgumentException("the user doesnt have the role " + r.getType().toString());
        }
    }

    // Can only be called by a Driver
    public Driver getDriverRole() throws UnsupportedOperationException {
        return (Driver) getRoleFromRoleType(Role.Type.DRIVER);
    }

    // Can only be called by a Customer
    public Customer getCustomerRole() throws UnsupportedOperationException {
        return (Customer) getRoleFromRoleType(Role.Type.CUSTOMER);
    }

    // Can only be called by an Operator
    public Operator getOperatorRole() throws UnsupportedOperationException {
        return (Operator) getRoleFromRoleType(Role.Type.OPERATOR);
    }

    // Can only be called by an User who as the RoleType passed as argument
    private Role getRoleFromRoleType(Role.Type roleType) {
        Iterator<Role> roleIterator = getRoles().iterator();
        Role role = null;
        while (roleIterator.hasNext() && role == null) {
            role = roleIterator.next();
            if (!role.getType().equals(roleType))
                role = null;
        }
        if (role == null)
            throw new UnsupportedOperationException("The User was not a " + roleType.toString());
        return role;
    }

}
