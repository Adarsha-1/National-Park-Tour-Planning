package org.example.repo;

import org.example.entity.ParkRides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkRidesRepository extends JpaRepository<ParkRides, Integer> {

    @Query("select p from ParkRides p where p.parkId.id =:parkId")
    public List<ParkRides> findByParkId(Integer parkId);

    @Query("select r from ParkRides r where r.rideId.id = :rideId")
    public List<ParkRides> findByRideId(Integer rideId);
}
