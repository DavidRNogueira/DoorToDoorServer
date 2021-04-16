package com.doortodoor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDto {

    private UUID id;
    private String address;
    private String city;
    private String state;
    private String country;
    private String name;
    private List<CoordinateDto> coordinates;
    private UUID organizationFk;
//    private List<LineDto> lines;
    private boolean isExpired;
    private String status;
    private Long timeSent;
    private int salvations;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CoordinateDto> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<CoordinateDto> coordinates) {
        this.coordinates = coordinates;
    }

    public UUID getOrganizationFk() {
        return organizationFk;
    }

    public void setOrganizationFk (UUID organizationFk) {
        this.organizationFk = organizationFk;
    }

//    public List<LineDto> getLines() {
//        return lines;
//    }
//
//    public void setLines(List<LineDto> lines) {
//        this.lines = lines;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
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
}
