package org.example.model;

import java.sql.Date;

public class AddParkModel {

    private String userName;

    private String email;

    private String parkName;
    private int numOfAdults;

    private int numOfChildren;

    private int numOfDays;

    private Date parkFromDate;

    private Date parkToDate;

    public int getNumOfAdults() {
        return numOfAdults;
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

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

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }

    private boolean oauth2;

    private String parkRide;

    private String parkTransportation;

    private String accommodation;

    private Date accFromDate;

    private Date accToDate;

    public Date getAccFromDate() {
        return accFromDate;
    }

    public void setAccFromDate(Date accFromDate) {
        this.accFromDate = accFromDate;
    }

    public Date getAccToDate() {
        return accToDate;
    }

    public void setAccToDate(Date accToDate) {
        this.accToDate = accToDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public boolean isOauth2() {
        return oauth2;
    }

    public void setOauth2(boolean oauth2) {
        this.oauth2 = oauth2;
    }

    public String getParkRide() {
        return parkRide;
    }

    public void setParkRide(String parkRide) {
        this.parkRide = parkRide;
    }

    public String getParkTransportation() {
        return parkTransportation;
    }

    public void setParkTransportation(String parkTransportation) {
        this.parkTransportation = parkTransportation;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    @Override
    public String toString() {
        return "AddParkModel{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", parkName='" + parkName + '\'' +
                ", numOfAdults=" + numOfAdults +
                ", numOfChildren=" + numOfChildren +
                ", numOfDays=" + numOfDays +
                ", parkFromDate=" + parkFromDate +
                ", parkToDate=" + parkToDate +
                ", oauth2=" + oauth2 +
                ", parkRide='" + parkRide + '\'' +
                ", parkTransportation='" + parkTransportation + '\'' +
                ", accommodation='" + accommodation + '\'' +
                ", accFromDate=" + accFromDate +
                ", accToDate=" + accToDate +
                '}';
    }
}
