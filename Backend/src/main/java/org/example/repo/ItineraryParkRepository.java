package org.example.repo;

import org.example.entity.ItineraryPark;
import org.example.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryParkRepository extends JpaRepository<ItineraryPark, Integer> {


    @Query("select i from ItineraryPark i where i.itineraryId.id = :itineraryId")
    public List<ItineraryPark> findByItinerary(Integer itineraryId);

    @Query("select i from ItineraryPark i where i.itineraryId.user.userName = :name")
    public List<ItineraryPark> findByUserName(String name);

    @Query("select i from ItineraryPark i where i.itineraryId.user.email = :email")
    public List<ItineraryPark> findByEmail(String email);


    @Query("select i from ItineraryPark i where i.itineraryId.user.email = :email and i.parkId.parkName = :parkName")
    public ItineraryPark findByEmailAndParkName(String email, String parkName);

    @Query("select i from ItineraryPark i where i.itineraryId.user.userName = :userName and i.parkId.parkName = :parkName")
    public ItineraryPark findByUserNameAndParkName(String userName, String parkName);

}
