package org.example.repo;

import org.example.entity.OtpVerification;
import org.example.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Integer> {

    @Query("select o from OtpVerification o where o.token = :token")
    OtpVerification findByToken(Integer token);

    @Query("select v from OtpVerification v where v.userId.id =:userId")
    public List<OtpVerification> findByUserId(Integer  userId);
}
