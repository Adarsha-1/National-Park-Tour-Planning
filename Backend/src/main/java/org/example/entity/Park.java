package org.example.entity;

import javax.persistence.*;

@Entity
@Table(schema = "dop")
public class Park {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "parkName")
    private String parkName;

    @Column(name = "address")
    private String address;

    @Column(name = "description",length=10485760)
    private String description;

    @Column(name = "entryPrice")
    private Double entryPrice;

    @ManyToOne(targetEntity = State.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "stateId")
    private State stateId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(Double entryPrice) {
        this.entryPrice = entryPrice;
    }

    @Override
    public String toString() {
        return "Park{" +
                "id=" + id +
                ", parkName='" + parkName + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", entryPrice='" + entryPrice + '\'' +
                ", state=" + stateId +
                '}';
    }
}
