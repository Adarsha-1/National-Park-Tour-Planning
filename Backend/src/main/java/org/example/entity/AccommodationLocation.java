package org.example.entity;


import javax.persistence.*;

@Entity
@Table(schema = "dop")
public class AccommodationLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne(targetEntity = Accommodation.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "accommodationId")
    private Accommodation accommodationId;

    @ManyToOne(targetEntity = Park.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "parkId")
    private Park parkId;

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

    public Accommodation getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Accommodation accommodationId) {
        this.accommodationId = accommodationId;
    }

    @Override
    public String toString() {
        return "AccommodationLocation{" +
                "id=" + id +
                ", accommodationId=" + accommodationId +
                ", parkId=" + parkId +
                '}';
    }
}
