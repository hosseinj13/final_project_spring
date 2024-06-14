package com.example.homeservice.repository;

import com.example.homeservice.model.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public  class AdminRepository {


    public static List<Admin> ADMINS;

    //database
    static {
        ADMINS = List.of(
                new Admin(1L, "hosseinj13", "h1374308N@")
        );
    }

    public void update(Admin admin) {
        List<Admin> collect = new java.util.ArrayList<>(ADMINS.stream()
                .filter(a -> !Objects.equals(a.getId(), admin.getId()))
                .toList());
        collect.add(admin);
        ADMINS = collect;
    }

    public Optional<Admin> findByUsernameAndPassword(String username, String password) {
        return ADMINS.stream()
                .filter(a -> a.getUsername().equals(username) && a.getPassword().equals(password))
                .findFirst();
    }



    //   public abstract Optional<Admin> findByUsername(String username);

   // public  Admin findByUsernameAndPassword(String username, String password);

//    @Modifying
//    @Query("UPDATE Admin u SET u.username = :username WHERE u.id = :id")
  //  public  void updateUsername(String username, Long id);

}
