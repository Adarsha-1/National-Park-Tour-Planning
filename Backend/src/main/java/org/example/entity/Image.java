package org.example.entity;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(schema = "dop")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    //@Lob
    @Column(name = "picByte")
    private byte[] picByte;

    @ManyToOne(targetEntity = Park.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "parkId")
    private Park parkId;

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

    public Park getParkId() {
        return parkId;
    }

    public void setParkId(Park parkId) {
        this.parkId = parkId;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", picByte=" + Arrays.toString(picByte) +
                ", park=" + parkId +
                '}';
    }
}
