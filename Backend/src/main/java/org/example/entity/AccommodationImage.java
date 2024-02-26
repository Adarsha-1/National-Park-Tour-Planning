package org.example.entity;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(schema = "dop")
public class AccommodationImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    //@Lob
    @Column(name = "picByte")
    private byte[] picByte;

    @ManyToOne(targetEntity = Accommodation.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "accommodationId")
    private Accommodation accommodationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }

    public Accommodation getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Accommodation accommodationId) {
        this.accommodationId = accommodationId;
    }

    @Override
    public String toString() {
        return "AccommodationImage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", picByte=" + Arrays.toString(picByte) +
                ", accommodationId=" + accommodationId +
                '}';
    }
}
