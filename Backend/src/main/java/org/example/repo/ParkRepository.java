package org.example.repo;

import org.example.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkRepository extends JpaRepository<Park, Integer> {

    @Query("select p from Park p where p.parkName = :parkName")
    public Park findByParkName(String parkName);

    @Query("select p from Park p where p.stateId.id = :stateId")
    public List<Park> findByState(Integer stateId);

    @Query("select p from Park p where p.id = :parkId")
    public Park findByParkId(Integer parkId);
}
