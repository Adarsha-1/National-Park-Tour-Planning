package org.example.repo;


import org.example.entity.ItineraryParkTransportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryParkTransportationRepository extends JpaRepository<ItineraryParkTransportation, Integer> {

    @Query("select prt from ItineraryParkTransportation prt where prt.parkId.id = :parkId and prt.transportationId.id = :transportationId")
    public List<ItineraryParkTransportation> searchByIdAndName(Integer parkId, Integer transportationId);

    @Query("select prt from ItineraryParkTransportation prt where prt.parkId.id = :parkId")
    public List<ItineraryParkTransportation> searchById(Integer parkId);

    @Query("select prt from ItineraryParkTransportation prt where prt.parkId.id = :parkId")
    public ItineraryParkTransportation findByItineraryId(Integer parkId);

}
