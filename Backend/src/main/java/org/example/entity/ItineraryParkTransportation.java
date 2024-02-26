package org.example.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(schema = "dop")
public class ItineraryParkTransportation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(targetEntity = ItineraryPark.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "parkId")
    private ItineraryPark parkId;

    @ManyToOne(targetEntity = Transportation.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "transportationId")
    private Transportation transportationId;

    @Column(name = "fromDate")
    private Date fromDate;

    @Column(name = "toDate")
    private Date toDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItineraryPark getParkId() {
        return parkId;
    }

    public void setParkId(ItineraryPark parkId) {
        this.parkId = parkId;
    }

    public Transportation getTransportationId() {
        return transportationId;
    }

    public void setTransportationId(Transportation transportationId) {
        this.transportationId = transportationId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "ItineraryParkTransportation{" +
                "id=" + id +
                ", parkId=" + parkId +
                ", transportationId=" + transportationId +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
