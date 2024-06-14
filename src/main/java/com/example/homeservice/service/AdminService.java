package com.example.homeservice.service;

import com.example.homeservice.model.Admin;
import com.example.homeservice.model.SecurityContext;
import com.example.homeservice.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Admin findByUsernameAndPassword(String username, String password) {
        Optional<Admin> byUsername = adminRepository.findByUsernameAndPassword(username, password);
        return byUsername.orElse(null);
    }

    public void update(String newUsername) {
        Admin admin = new Admin(SecurityContext.id, newUsername);
        adminRepository.update(admin);
    }
}
