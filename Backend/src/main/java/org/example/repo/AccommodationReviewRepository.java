package org.example.repo;

import org.example.entity.AccommodationReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationReviewRepository extends JpaRepository<AccommodationReview, Long> {

    @Query("select a from AccommodationReview a where a.accommodationId.id = :id")
    public List<AccommodationReview> findByAccommodationId(Long id);
}
