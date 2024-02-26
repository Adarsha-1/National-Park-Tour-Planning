package org.example.repo;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email = :email")
    public User findByEmail(String email);

    @Query("select u from User u where u.userName = :name")
    public User findByUserName(String name);

    @Query("select u from User u where u.contactNo = :contactNo")
    public User findByContactNumber(Long contactNo);
}
