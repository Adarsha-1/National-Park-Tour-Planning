package org.example.model;

import org.example.entity.Rides;

import java.util.List;

public class ParkModel {

    private String parkName;

    private String address;

    private List<byte[]> images;

    private String description;

    private Double price;

    private List<Rides> rideList;

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Rides> getRideList() {
        return rideList;
    }

    public void setRideList(List<Rides> rideList) {
        this.rideList = rideList;
    }

    @Override
    public String toString() {
        return "ImageModel{" +
                "parkName='" + parkName + '\'' +
                ", address='" + address + '\'' +
                ", image=" + images +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rideList=" + rideList +
                '}';
    }
}
