package org.example.repo;


import org.example.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("select p from Payment p where p.id = :id")
    public Payment findPaymentById(Integer id);

    @Query("select p from Payment p where p.itineraryId.user.email = :email")
    public Payment findPaymentByEmail(String email);

    @Query("select p from Payment p where p.itineraryId.user.userName = :userName")
    public Payment findPaymentByUserName(String userName);
}
