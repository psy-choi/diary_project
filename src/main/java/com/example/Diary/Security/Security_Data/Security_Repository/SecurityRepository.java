package com.example.Diary.Security.Security_Data.Security_Repository;

import com.example.Diary.Security.Security_Data.Entity.Security_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SecurityRepository extends JpaRepository<Security_Entity, Long> {
    Security_Entity findByRefreshToken(String refreshToken);
    boolean existsByKeyEmail(String userEmail);

    @Transactional
    void deleteByKeyEmail(String userEmail);
}
