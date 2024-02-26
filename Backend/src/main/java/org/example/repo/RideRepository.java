package org.example.repo;

import org.example.entity.Rides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Rides, Integer> {

    @Query("select r from Rides r where r.id =:rideId")
    public Rides findRideById(Integer rideId);

    @Query("select r from Rides r where r.name =:ride")
    public Rides findByName(String ride);
}
