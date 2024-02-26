package org.example.repo;



import org.example.entity.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Integer> {


    @Query("select r from Transportation r where r.name =:transportation")
    public Transportation findByName(String transportation);
}
