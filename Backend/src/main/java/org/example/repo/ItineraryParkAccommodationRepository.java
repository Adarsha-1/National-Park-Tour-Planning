package org.example.repo;


import org.example.entity.ItineraryParkAccommodation;
import org.example.entity.ItineraryParkRides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryParkAccommodationRepository extends JpaRepository<ItineraryParkAccommodation, Integer> {

    @Query("select pr from ItineraryParkAccommodation pr where pr.parkId.id = :parkId and pr.accommodationId.id = :accommodationId")
    public List<ItineraryParkAccommodation> searchByIdAndName(Integer parkId, Long accommodationId);

    @Query("select pr from ItineraryParkAccommodation pr where pr.parkId.id = :parkId")
    public List<ItineraryParkAccommodation> searchById(Integer parkId);

    @Query("select pr from ItineraryParkAccommodation pr where pr.parkId.id = :parkId")
    public ItineraryParkAccommodation findByParkId(Integer parkId);
}
