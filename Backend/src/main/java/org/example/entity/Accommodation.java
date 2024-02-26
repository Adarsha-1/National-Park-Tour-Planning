package org.example.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(schema = "dop")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accommodationName")
    private String accommodationName;

    @OneToOne(targetEntity = AccommodationType.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "accommodationTypeId")
    private AccommodationType accommodationTypeId;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public AccommodationType getAccommodationTypeId() {
        return accommodationTypeId;
    }

    public void setAccommodationTypeId(AccommodationType accommodationTypeId) {
        this.accommodationTypeId = accommodationTypeId;
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

    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", accommodationName='" + accommodationName + '\'' +
                ", accommodationTypeId=" + accommodationTypeId +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
