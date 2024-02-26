package org.example.repo;

import org.example.entity.AccommodationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationImageRepository extends JpaRepository<AccommodationImage, Long> {

    @Query("select a from AccommodationImage a where a.accommodationId.id =:accommodationId")
    public List<AccommodationImage> findByAId(Long accommodationId);
}
