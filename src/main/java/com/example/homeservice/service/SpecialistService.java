package com.example.homeservice.service;

import com.example.homeservice.enums.SpecialistStatus;
import com.example.homeservice.exception.InformationDuplicateException;
import com.example.homeservice.exception.NotFoundException;
import com.example.homeservice.model.Specialist;
import com.example.homeservice.model.SubService;
import com.example.homeservice.repository.SpecialistRepository;
import com.example.homeservice.repository.SubServiceRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final SubServiceRepository subServiceRepository;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();


    @Transactional
    public Specialist updateSpecialistWithSubServices(Long specialistId, Set<SubService> subServices) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new NotFoundException("Specialist not found"));
        specialist.setSubServices(subServices);
        return specialistRepository.save(specialist);
    }
    @Transactional
    public Optional<Specialist> findById(Long id) {
        return specialistRepository.findById(id);
    }

    @Transactional
    public Specialist save(Specialist specialist) {
        if (specialistRepository.findByUsername(specialist.getUsername()).isPresent())
            throw new InformationDuplicateException(
                    specialist.getUsername() + " is duplicate"
            );
        return specialistRepository.save(specialist);
    }

    public void removeById(Long id) {
        Specialist specialist = specialistRepository.findById(id).orElseThrow(
                () -> new NotFoundException("specialist with id " + id + " not found")
        );
        specialistRepository.delete(specialist);
    }

    public void removeByUsername(String username){
        Specialist specialist = specialistRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("specialist with username " + username + " not found")
        );
        specialistRepository.delete(specialist);
    }

    public Specialist update(Specialist specialist, Long id, Set<SubService> newSubServices) {
        Specialist foundedSpecialist = specialistRepository.findById(id).orElseThrow(
                () -> new NotFoundException("specialist with id " + id + " not found")
        );
        Optional.ofNullable(specialist.getUsername()).ifPresent(foundedSpecialist::setUsername);
        Optional.ofNullable(specialist.getFirstName()).ifPresent(foundedSpecialist::setFirstName);
        Optional.ofNullable(specialist.getLastName()).ifPresent(foundedSpecialist::setLastName);
        Optional.ofNullable(specialist.getEmail()).ifPresent(foundedSpecialist::setEmail);
        Optional.ofNullable(specialist.getPhoneNumber()).ifPresent(foundedSpecialist::setPhoneNumber);
        Optional.of(specialist.getYearsOfExperience()).ifPresent(foundedSpecialist::setYearsOfExperience);
        Optional.of(specialist.getProfilePicture()).ifPresent(foundedSpecialist::setProfilePicture);
        Optional.of(specialist.getStatus()).ifPresent(foundedSpecialist::setStatus);
        Optional.ofNullable(newSubServices).ifPresent(specialist::setSubServices);
        Optional.ofNullable(specialist.getPassword()).ifPresent(foundedSpecialist::setPassword);
        return specialistRepository.save(foundedSpecialist);
    }

    @Transactional
    public void updateUsername(Long id, String newUsername) {
        if (specialistRepository.findById(id).isPresent()) {
            specialistRepository.updateUsername(newUsername, id);
        } else {
            throw new NotFoundException("specialist with id " + id + " not found");
        }
    }


    public Specialist updateStatus(String username, SpecialistStatus status) {
        return null;
    }

    public Specialist findByUsernameAndPassword(String username, String password){
        return specialistRepository.findByUsernameAndPassword(username, password);
    }

    public boolean validate(Specialist specialist) {
        Set<ConstraintViolation<Specialist>> violations = validator.validate(specialist);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Specialist> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
        return false;
    }

    public Optional<Specialist> findByUsername(String  username) {
        return specialistRepository.findByUsername(username);
    }

    @Transactional
    public List<Specialist> findByStatus(SpecialistStatus status){
        return specialistRepository.findByStatus(status);
    }

    public List<Specialist> findAllByStatus(SpecialistStatus status){
        return specialistRepository.findAllByStatus(status);
    }
    public List<Specialist> findAll(){
        return specialistRepository.findAll();
    }

    @Transactional
    public Specialist findByIdWithSubServices(Long specialistId) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new NotFoundException("Specialist not found with id " + specialistId));
        // Ensure subServices are loaded
        specialist.getSubServices().size();
        return specialist;
    }

    @Transactional
    public List<Specialist> findAllByStatusWithSubServices(SpecialistStatus status) {
        List<Specialist> specialists = specialistRepository.findAllByStatus(status);
        specialists.forEach(specialist -> specialist.getSubServices().size());
        return specialists;
    }

    @Transactional
    public List<Specialist> findByStatusIn(List<SpecialistStatus> list) {
        return specialistRepository.findByStatusIn(list);
    }

    public List<Specialist> findBySubService(SubService subService) {
        return specialistRepository.findBySubService(subService);
    }
}

