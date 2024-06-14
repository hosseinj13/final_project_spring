package com.example.homeservice.repository;

import com.example.homeservice.model.MainService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainServiceRepository extends JpaRepository<MainService, Long> {
    Optional<MainService> findByServiceName(String name);

    @Modifying
    @Query("UPDATE MainService s SET s.serviceName = :name WHERE s.id = :id")
    void updateName(String name, long id);
}
