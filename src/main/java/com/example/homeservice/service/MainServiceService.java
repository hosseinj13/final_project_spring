package com.example.homeservice.service;

import com.example.homeservice.exception.InformationDuplicateException;
import com.example.homeservice.exception.NotFoundException;
import com.example.homeservice.model.MainService;
import com.example.homeservice.repository.MainServiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainServiceService {

    private final MainServiceRepository serviceRepository;

    public MainService save(MainService service) {
        if (serviceRepository.findByServiceName(service.getServiceName()).isPresent())
            throw new InformationDuplicateException(
                    service.getServiceName() + " is duplicate"
            );
        return serviceRepository.save(service);
    }

    public Optional<MainService> findById(Long id){
        return serviceRepository.findById(id);
    }
    public void removeById(Long id) {
        MainService service = serviceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("service with id " + id + " not found")
        );
        serviceRepository.delete(service);
    }


    @Transactional
    public MainService update(MainService service, Long id) throws NotFoundException{
        MainService foundedService = serviceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("MainService with id " + id + " not found")
        );
        Hibernate.initialize(service.getSubServices());
        Optional.ofNullable(service.getServiceName()).ifPresent(foundedService::setServiceName);
        Optional.ofNullable(service.getSubServices()).ifPresent(foundedService::setSubServices);
        return serviceRepository.save(foundedService);
    }

    @Transactional
    public void updateUsername(Long id, String newServiceName) {
        if (serviceRepository.findById(id).isPresent()) {
            serviceRepository.updateName(newServiceName, id);
        } else {
            throw new NotFoundException("user with id " + id + " not found");
        }
    }

    public List<MainService> findAll() {
        return serviceRepository.findAll();
    }

    public Optional<MainService> findByServiceName(String name){
        return serviceRepository.findByServiceName(name);
    }
}
