package org.example.repo;

import org.example.entity.ItineraryParkRides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryParkRidesRepository extends JpaRepository<ItineraryParkRides, Integer> {

    @Query("select pr from ItineraryParkRides pr where pr.parkId.id = :parkId and pr.rideId.id = :rideId")
    public List<ItineraryParkRides> searchByIdAndName(Integer parkId, Integer rideId);

    @Query("select pr from ItineraryParkRides pr where pr.parkId.id = :parkId")
    public List<ItineraryParkRides> searchById(Integer parkId);

    @Query("select pr from ItineraryParkRides pr where pr.parkId.id = :parkId and pr.rideId.id = :rideId")
    public ItineraryParkRides searchByIdAndNameofRide(Integer parkId, Integer rideId);
    
}
