package com.example.homeservice.repository;

import com.example.homeservice.model.MainService;
import com.example.homeservice.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService, Long> {
    Optional<SubService> findSubServiceBySubserviceName(String name);

    @Modifying
    @Query("UPDATE SubService s SET s.subserviceName = :name WHERE s.id = :id")
    void updateSubServiceName(String name, Long id);

    List<SubService> findByServiceId(Long serviceId);

    List<SubService> findByService(MainService mainService);

    Optional<SubService> findBySubserviceName(String subService);

    Optional<SubService> findBySubserviceNameAndService(String subServiceName, MainService mainService);

}
