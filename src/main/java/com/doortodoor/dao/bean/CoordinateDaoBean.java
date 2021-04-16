package com.doortodoor.dao.bean;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="coordinate")
public class CoordinateDaoBean {

    @Id
    @GeneratedValue(generator = "id-generator")
    @Type(type = "uuid-char")
    @Column(name="id")
    private UUID id;

    @Column(name="lat")
    private double lat;

    @Column(name="lng")
    private double lng;

    @ManyToOne
    @JoinColumn(name="taskFk")
    private TaskDaoBean task;

//    @ManyToOne
//    @JoinColumn(name="lineFk")
//    private  LineEntity line;

    @Column(name = "orderNumber")
    private int orderNumber;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public TaskDaoBean getTask() {
        return task;
    }

    public void setTask(TaskDaoBean task) {
        this.task = task;
    }

//    public LineEntity getLine() {
//        return line;
//    }
//
//    public void setLine(LineEntity line) {
//        this.line = line;
//    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

}
