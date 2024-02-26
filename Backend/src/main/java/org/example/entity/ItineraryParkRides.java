package org.example.entity;

import javax.persistence.*;

@Entity
@Table(schema = "dop")
public class ItineraryParkRides {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(targetEntity = ItineraryPark.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "parkId")
    private ItineraryPark parkId;

    @ManyToOne(targetEntity = Rides.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "rideId")
    private Rides rideId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rides getRideId() {
        return rideId;
    }

    public void setRideId(Rides rideId) {
        this.rideId = rideId;
    }

    public ItineraryPark getParkId() {
        return parkId;
    }

    public void setParkId(ItineraryPark parkId) {
        this.parkId = parkId;
    }

    @Override
    public String toString() {
        return "ItineraryParkRides{" +
                "id=" + id +
                ", parkId=" + parkId +
                ", rideId=" + rideId +
                '}';
    }
}
