package org.example.repo;

import org.example.entity.AccommodationLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationLocationRepo extends JpaRepository<AccommodationLocation, Integer> {

    @Query("select a from AccommodationLocation a where a.accommodationId.id = :id")
    public AccommodationLocation findByAId(Long id);

    @Query("select a from AccommodationLocation a where a.parkId.id = :id")
    public List<AccommodationLocation> findByParkId(Integer id);
}
