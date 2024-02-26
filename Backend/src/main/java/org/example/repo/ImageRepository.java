package org.example.repo;

import org.example.entity.Image;
import org.example.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query("select i from Image i where i.name = :name")
    public Image findByName(String name);

    @Query("select i from Image i where i.parkId.id = :parkId")
    public List<Image> findByParkId(Integer parkId);
}
