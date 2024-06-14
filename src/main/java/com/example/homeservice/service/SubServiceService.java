package com.example.homeservice.service;

import com.example.homeservice.exception.InformationDuplicateException;
import com.example.homeservice.exception.NotFoundException;
import com.example.homeservice.model.MainService;
import com.example.homeservice.model.Specialist;
import com.example.homeservice.model.SubService;
import com.example.homeservice.repository.SubServiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubServiceService {

    private final SubServiceRepository subServiceRepository;

    public SubService save(SubService subService) {
        if (subServiceRepository.findSubServiceBySubserviceName(subService.getSubserviceName()).isPresent())
            throw new InformationDuplicateException(
                    subService.getSubserviceName() + " is duplicate"
            );
        return subServiceRepository.save(subService);
    }

    public void removeById(Long id) {
        SubService subService = subServiceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("subService with id " + id + " not found")
        );
        subServiceRepository.delete(subService);
    }

    @Transactional
    public SubService update(SubService subService, Long id,  Set<Specialist> specialists) {
        SubService foundedSubService = subServiceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("subService with id " + id + " not found")
        );
        Optional.ofNullable(subService.getSubserviceName()).ifPresent(foundedSubService::setSubserviceName);
        Optional.of(subService.getPrice()).ifPresent(foundedSubService::setPrice);
        Optional.ofNullable(subService.getDescription()).ifPresent(foundedSubService::setDescription);
        Optional.ofNullable(subService.getService()).ifPresent(foundedSubService::setService);
        Optional.ofNullable(specialists).ifPresent(subService::setSpecialists);
        return subServiceRepository.save(foundedSubService);
    }

    @Transactional
    public void updateSubServiceName(Long id, String newSubServiceName) {
        if (subServiceRepository.findById(id).isPresent()) {
            subServiceRepository.updateSubServiceName(newSubServiceName, id);
        } else {
            throw new NotFoundException("subService with id " + id + " not found");
        }
    }

    public List<SubService> findByServiceId(Long serviceId) {
        return subServiceRepository.findByServiceId(serviceId);
    }

    @Transactional
    public Optional<SubService> findById(Long id) {
        return Optional.ofNullable(subServiceRepository.findById(id).orElse(null));
    }

    @Transactional
    public List<SubService> findByMainService(MainService mainService){
        return subServiceRepository.findByService(mainService);
    }

    @Transactional
    public List<SubService> findAll(){
        return subServiceRepository.findAll();
    }

    public Optional<SubService> findBySubserviceNameAndService(String subServiceName, MainService mainService){
        return subServiceRepository.findBySubserviceNameAndService(subServiceName, mainService);
    }

    public Optional<SubService> findBySubserviceName(String subService){
        return subServiceRepository.findBySubserviceName(subService);
    }
}
