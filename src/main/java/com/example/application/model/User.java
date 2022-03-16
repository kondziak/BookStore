package com.example.application.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Payment> userPayments;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<Billing> billingList;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    private ShoppingCart shoppingCart;

    public User() {
    }

    public User(String first_name, String last_name, String email, String password, Collection<Role> roles) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public List<Payment> getUserPayments() {
        return userPayments;
    }

    public void setUserPayments(List<Payment> userPayments) {
        this.userPayments = userPayments;
    }

    public List<Billing> getBillingList() {
        return billingList;
    }

    public void setBillingList(List<Billing> billingList) {
        this.billingList = billingList;
    }
}
