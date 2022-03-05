package com.projectdemo.twostepverifier.repository;

import com.projectdemo.twostepverifier.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT CASE WHEN COUNT (u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = ?1")
    Boolean selectExistEmail(String email);
}
