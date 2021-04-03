package com.doortodoor.dao.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name="user")
public class UserDaoBean {
    @Column(name="firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="country")
    private String country;

    @ManyToOne
    @JoinColumn(name="fkOrganization")
    private OrganizationDaoBean organization;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="role")
    private String role;

    @Id
    @GeneratedValue(generator = "id-generator")
    @GenericGenerator(name = "id-generator",
            strategy = "com.example.api.shared.IdGenerator")
    @Column(name="id")
    private String id;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public OrganizationDaoBean getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDaoBean organization) {
        this.organization = organization;
    }
}

