package com.example.homeservice.menu;

import com.example.homeservice.service.*;
import com.example.homeservice.util.PasswordGenerator;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Main {
    private final AdminService adminService;
    private final AddressService addressService;
    private final CustomerService customerService;
    private final SpecialistService specialistService;
    private final MainServiceService serviceService;
    private final PasswordGenerator passwordGenerator;
    private final SubServiceService subServiceService;
    private final OrderService orderService;
    private final OfferService offerService;
    private final CommentService commentService;
    private final Validator validator;


    public Main(AdminService adminService, CustomerService customerService, SpecialistService specialistService, PasswordGenerator passwordGenerator, MainServiceService serviceService, SubServiceService subServiceService, OrderService orderService, OfferService offerService, CommentService commentService, AddressService addressService, Validator validator) {
        this.adminService = adminService;
        this.customerService = customerService;
        this.specialistService = specialistService;
        this.passwordGenerator = passwordGenerator;
        this.serviceService = serviceService;
        this.subServiceService = subServiceService;
        this.orderService = orderService;
        this.offerService = offerService;
        this.commentService = commentService;
        this.addressService = addressService;
        this.validator = validator;
    }


    @PostConstruct
    public void runTest() {

        Menu menu = new Menu(adminService, customerService, specialistService, addressService, serviceService, subServiceService, orderService, offerService, commentService, validator);
        menu.startMenu();
    }
}