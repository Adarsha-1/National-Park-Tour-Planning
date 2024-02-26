package org.example.repo;

import org.example.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    @Query("select v from VerificationToken v where v.token = :token")
    public VerificationToken findByToken(String token);

    @Query("select v from VerificationToken v where v.userId.id =:userId")
    public List<VerificationToken> findByUserId(Integer  userId);
}
