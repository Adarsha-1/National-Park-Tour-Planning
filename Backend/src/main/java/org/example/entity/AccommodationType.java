package org.example.entity;

import javax.persistence.*;

@Entity
@Table(schema = "dop")
public class AccommodationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AccomodationType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
