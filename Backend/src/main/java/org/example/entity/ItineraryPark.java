package org.example.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(schema = "dop")
public class ItineraryPark {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(targetEntity = Park.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "parkId")
    private Park parkId;

    @OneToOne(targetEntity = Itinerary.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "itineraryId")
    private Itinerary itineraryId;



    @Column(name = "numOfAdults")
    private int numOfAdults;

    @Column(name = "numOfChildren")
    private int numOfChildren;

    @Column(name = "numberOfDays")
    private int numOfDays;

    @Column(name = "parkFromDate")
    private Date parkFromDate;

    @Column(name = "parkToDate")
    private Date parkToDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Park getParkId() {
        return parkId;
    }

    public void setParkId(Park parkId) {
        this.parkId = parkId;
    }

    public Itinerary getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Itinerary itineraryId) {
        this.itineraryId = itineraryId;
    }

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

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
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
}
