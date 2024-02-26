package org.example.repo;


import org.example.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {

    @Query("select i from Itinerary i where i.id =:id")
    public Itinerary findByItineraryId(Integer id);

    @Query("select i from Itinerary i where i.user.id =:id")
    public Itinerary findByUserId(Integer id);

    @Query("select i from Itinerary i where i.user.email =:email")
    public Itinerary findByUserEmail(String email);

    @Query("select i from Itinerary i where i.user.userName =:userName")
    public Itinerary findByUserName(String userName);

}
