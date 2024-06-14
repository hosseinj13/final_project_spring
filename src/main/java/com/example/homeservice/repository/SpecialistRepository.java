package com.example.homeservice.repository;

import com.example.homeservice.enums.SpecialistStatus;
import com.example.homeservice.model.Specialist;
import com.example.homeservice.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
    Optional<Specialist> findByUsername(String username);

    List<Specialist> findByStatus(SpecialistStatus status);
    List<Specialist> findAllByStatus(SpecialistStatus status);
    List<Specialist> findByStatusIn(List<SpecialistStatus> specialistStatusList);
    Specialist findByUsernameAndPassword(String username, String password);

    @Query("SELECT s FROM Specialist s JOIN s.subServices ss WHERE ss = :subService")
    List<Specialist> findBySubService(SubService subService);
    @Modifying
    @Query("UPDATE Specialist u SET u.username = :username WHERE u.id = :id")
    void updateUsername(String username, Long id);


}