package org.example.repo;

import org.example.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("select a from Accommodation a where a.accommodationTypeId.id = :id")
    public List<Accommodation> findByType(Integer id);

    @Query("select a from Accommodation a where a.id = :id")
    public Accommodation findByAId(Long id);

    @Query("select a from Accommodation a where a.accommodationName = :accommodationName")
    public Accommodation findByAccommodationName(String accommodationName);
}
