package com.example.Backend.repository;

import com.example.Backend.entity.Pg;
import com.example.Backend.entity.SavedPg;
import com.example.Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedPgRepository extends JpaRepository<SavedPg, Long> {

    List<SavedPg> findByUser(User user);

    Optional<SavedPg> findByUserAndPg(User user, Pg pg);

    boolean existsByUserAndPg(User user, Pg pg);

    void deleteByUserAndPg(User user, Pg pg);
     long countByUser(User user);
}

