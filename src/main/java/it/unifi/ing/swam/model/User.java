package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @OneToMany(cascade={CascadeType.PERSIST})
    @JoinColumn(name="user_id")
    private List<Role> roles;

    @ManyToOne
    private Agency agency;

    @OneToMany(mappedBy = "operator", fetch=FetchType.LAZY)
    private List<Waybill> operatorWaybills; //Operator

    @OneToMany(mappedBy = "sender", fetch=FetchType.LAZY)
    private List<Waybill> customerWaybills; //Customer

    public User() {

    }

    public User(String uuid) {
        super(uuid);
        this.roles = new ArrayList<>();
        this.operatorWaybills = new ArrayList<>();
        this.customerWaybills = new ArrayList<>();
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

    public void addRoles(Role r) {
        this.roles.add(r);
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

    public List<Waybill> getOperatorWaybills() {
        return operatorWaybills;
    }

    public void addOperatorWaybill(Waybill w) {
        this.operatorWaybills.add(w);
    }

    public List<Waybill> getCustomerWaybills() {
        return customerWaybills;
    }

    public void addCustomerWaybill(Waybill w) {
        this.customerWaybills.add(w);
    }

}
