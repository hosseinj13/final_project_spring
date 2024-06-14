package com.example.homeservice.service;

import com.example.homeservice.exception.NotFoundException;
import com.example.homeservice.model.Address;
import com.example.homeservice.model.Customer;
import com.example.homeservice.model.Order;
import com.example.homeservice.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AddressService {
    private final AddressRepository addressRepository;

    public Address findById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }
    public Address save(Address address) {
//        if (addressRepository.findByZipCode(address.getZipCode()).isPresent())
//            throw new InformationDuplicateException(
//                    address.getZipCode() + " is duplicate"
//            );
        return addressRepository.save(address);
    }

    public void removeById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new NotFoundException("address with id " + id + " not found")
        );
        addressRepository.delete(address);
    }

    public Address update(Address address, Long id) {
        Address foundedAddress = addressRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Address with id " + id + " not found")
        );
        Optional.ofNullable(address.getCity()).ifPresent(foundedAddress::setCity);
        Optional.ofNullable(address.getState()).ifPresent(foundedAddress::setState);
        Optional.ofNullable(address.getAddressLine()).ifPresent(foundedAddress::setAddressLine);
        Optional.ofNullable(address.getZipCode()).ifPresent(foundedAddress::setZipCode);
        Optional.ofNullable(address.getCustomer()).ifPresent(foundedAddress::setCustomer);
        if (address.getOrders() != null) {
            foundedAddress.getOrders().clear();
            foundedAddress.getOrders().addAll(address.getOrders());
            for (Order order : foundedAddress.getOrders()) {
                order.setAddress(foundedAddress);
            }
        }
        return addressRepository.save(foundedAddress);
    }

    @Transactional
    public void updateStreet(Long id, String newStreet) {
        if (addressRepository.findById(id).isPresent()) {
            addressRepository.updateStreet(newStreet, id);
        } else {
            throw new NotFoundException("address with id " + id + " not found");
        }
    }

    public List<Address> findByCustomer(Customer customer){
        return addressRepository.findByCustomer(customer);
    }

    public boolean existsByPostalCode(String postalCode) {
        return addressRepository.existsByZipCode(postalCode);
    }
}
