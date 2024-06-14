package com.example.homeservice.service;

import com.example.homeservice.exception.InformationDuplicateException;
import com.example.homeservice.exception.NotFoundException;
import com.example.homeservice.model.Customer;
import com.example.homeservice.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerService {


    private final CustomerRepository customerRepository;

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customerRepository.findById(id).orElse(null));
    }
    public Customer save(Customer customer) {
        if (customerRepository.findByUsername(customer.getUsername()).isPresent())
            throw new InformationDuplicateException(
                    customer.getUsername() + " is duplicate"
            );
        return customerRepository.save(customer);
    }

    public void removeById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("customer with id " + id + " not found")
        );
        customerRepository.delete(customer);
    }

    public void removeByUsername(String username){
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("customer with username " + username + " not found")
        );
        customerRepository.delete(customer);
    }

    public Customer update(Customer customer, Long id) {
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("customer with id " + id + " not found")
        );
        Optional.ofNullable(customer.getUsername()).ifPresent(foundedCustomer::setUsername);
        Optional.ofNullable(customer.getPassword()).ifPresent(foundedCustomer::setPassword);
        Optional.ofNullable(customer.getFirstName()).ifPresent(foundedCustomer::setFirstName);
        Optional.ofNullable(customer.getLastName()).ifPresent(foundedCustomer::setLastName);
        Optional.ofNullable(customer.getEmail()).ifPresent(foundedCustomer::setEmail);
        Optional.ofNullable(customer.getPhoneNumber()).ifPresent(foundedCustomer::setPhoneNumber);
        Optional.ofNullable(customer.getAddresses()).ifPresent(foundedCustomer::setAddresses);
        return customerRepository.save(foundedCustomer);
    }

    @Transactional
    public void updateUsername(Long id, String newUsername) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.updateUsername(newUsername, id);
        } else {
            throw new NotFoundException("customer with id " + id + " not found");
        }
    }
    
    public Optional<Customer> findByUsername(String username){
        return customerRepository.findByUsername(username);
    }

    public Customer findByUsernameAndPassword(String username, String password){
        return customerRepository.findByUsernameAndPassword(username, password);
    }

    public boolean validate(Customer customer) {
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Customer> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
        return false;
    }
}
