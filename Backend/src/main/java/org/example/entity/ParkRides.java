package org.example.entity;

import javax.persistence.*;

@Entity
@Table(schema = "dop")
public class ParkRides {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(targetEntity = Park.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "parkId")
    private Park parkId;

    @ManyToOne(targetEntity = Rides.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "rideId")
    private Rides rideId;

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

    public Rides getRides() {
        return rideId;
    }

    public void setRides(Rides rides) {
        this.rideId = rides;
    }

    @Override
    public String toString() {
        return "ParkRides{" +
                "id=" + id +
                ", parkId=" + parkId +
                ", rides=" + rideId +
                '}';
    }
}
