package org.example.model;

import java.sql.Date;

public class ItineraryModel {

    private int id;

    private String parkName;

    private String accommodationName;

    private String accFromDate;

    private String accToDate;

    private String accPrice;

    private String transportation;

    private String transStartDate;

    private String parkLocation;

    private boolean completed;

    private Date parkFromDate;

    private Date parkToDate;

    public Date getParkFromDate() {
        return parkFromDate;
    }

    public void setParkFromDate(Date parkFromDate) {
        this.parkFromDate = parkFromDate;
    }

    public Date getParkToDate() {
        return parkToDate;
    }

    public void setParkToDate(Date parkToDate) {
        this.parkToDate = parkToDate;
    }

    public String getParkLocation() {
        return parkLocation;
    }

    public void setParkLocation(String parkLocation) {
        this.parkLocation = parkLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public String getAccFromDate() {
        return accFromDate;
    }

    public void setAccFromDate(String accFromDate) {
        this.accFromDate = accFromDate;
    }

    public String getAccToDate() {
        return accToDate;
    }

    public void setAccToDate(String accToDate) {
        this.accToDate = accToDate;
    }

    public String getAccPrice() {
        return accPrice;
    }

    public void setAccPrice(String accPrice) {
        this.accPrice = accPrice;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getTransStartDate() {
        return transStartDate;
    }

    public void setTransStartDate(String transStartDate) {
        this.transStartDate = transStartDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "ItineraryModel{" +
                "id=" + id +
                ", parkName='" + parkName + '\'' +
                ", accommodationName='" + accommodationName + '\'' +
                ", accFromDate='" + accFromDate + '\'' +
                ", accToDate='" + accToDate + '\'' +
                ", accPrice='" + accPrice + '\'' +
                ", transportation='" + transportation + '\'' +
                ", transStartDate='" + transStartDate + '\'' +
                ", parkLocation='" + parkLocation + '\'' +
                ", completed=" + completed +
                ", parkFromDate='" + parkFromDate + '\'' +
                ", parkToDate='" + parkToDate + '\'' +
                '}';
    }
}
