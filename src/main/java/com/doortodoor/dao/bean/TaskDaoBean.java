package com.doortodoor.dao.bean;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="task")
public class TaskDaoBean {

    @Id
    @GeneratedValue(generator = "id-generator")
    @Type(type = "uuid-char")
    @Column(name="id")
    private UUID id;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="phoneNumber")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name =  "country")
    private String country;

    @Column(name="name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name="timeSent")
    private Long timeSent;

    @Column(name="salvations")
    private int salvations;

    @Type(type = "uuid-char")
    @Column(name="organizationFk")
    private UUID organizationFk;

    @OneToMany( fetch = FetchType.EAGER, mappedBy = "task", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @Column(name = "coordinates")
    private List<CoordinateDaoBean> coordinates = new ArrayList<CoordinateDaoBean>();

    public void addCoordinate(final CoordinateDaoBean coordinatesEntity) {
        if(coordinates == null) {
            coordinates = new ArrayList();
        }
        coordinates.add(coordinatesEntity);
        coordinatesEntity.setTask(this);
    }

    public void removeCoordinates (final List<CoordinateDaoBean> coordinatesEntities){
        coordinates.removeAll(coordinatesEntities);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Long timeSent) {
        this.timeSent = timeSent;
    }

    public int getSalvations() {
        return salvations;
    }

    public void setSalvations(int salvations) {
        this.salvations = salvations;
    }

    public UUID getOrganizationFk() {
        return organizationFk;
    }

    public void setOrganizationFk(UUID organizationFk) {
        this.organizationFk = organizationFk;
    }

    public List<CoordinateDaoBean> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<CoordinateDaoBean> coordinates) {
        this.coordinates = coordinates;
    }
}
