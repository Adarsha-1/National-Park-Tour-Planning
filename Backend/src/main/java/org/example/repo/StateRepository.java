package org.example.repo;

import org.example.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {


    @Query("select s from State s where s.name =:stateName")
    public State findByName(String stateName);
}
