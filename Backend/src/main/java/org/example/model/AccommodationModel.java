package org.example.model;

import org.example.entity.AccommodationLocation;
import org.example.entity.AccommodationReview;

import java.util.List;

public class AccommodationModel {

    private String name;

    private String type;

    private String description;

    private String location;

    private List<AccommodationReview> reviewList;

    private List<byte[]> images;

    private Double price;

    private AccommodationLocation accommodationLocation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<AccommodationReview> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<AccommodationReview> reviewList) {
        this.reviewList = reviewList;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public AccommodationLocation getAccommodationLocation() {
        return accommodationLocation;
    }

    public void setAccommodationLocation(AccommodationLocation accommodationLocation) {
        this.accommodationLocation = accommodationLocation;
    }

    @Override
    public String toString() {
        return "AccommodationModel{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", reviewList=" + reviewList +
                ", images=" + images +
                ", price=" + price +
                ", accommodationLocation=" + accommodationLocation +
                '}';
    }
}
