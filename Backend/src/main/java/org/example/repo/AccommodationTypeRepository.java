package org.example.repo;

import org.example.entity.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationTypeRepository extends JpaRepository<AccommodationType, Integer> {

    @Query("select a from AccommodationType a where a.type = :type")
    public AccommodationType findByType(String type);
}
