package org.example.entity;


import javax.persistence.*;

@Entity
@Table(schema = "dop")
public class AccommodationReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Accommodation.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "accommodationId")
    private Accommodation accommodationId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "review")
    private String review;

    @Column(name = "rating")
    private float rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accommodation getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Accommodation accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "AccommodationReview{" +
                "id=" + id +
                ", accommodationId=" + accommodationId +
                ", userName='" + userName + '\'' +
                ", review='" + review + '\'' +
                ", rating=" + rating +
                '}';
    }
}
