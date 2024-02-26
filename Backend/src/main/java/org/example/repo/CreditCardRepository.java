package org.example.repo;

import org.example.entity.CreditCard;
import org.example.entity.Image;
import org.example.entity.State;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

    @Query("select c from CreditCard c where c.id = :id")
    public CreditCard findByCreditId(Integer id);

}
