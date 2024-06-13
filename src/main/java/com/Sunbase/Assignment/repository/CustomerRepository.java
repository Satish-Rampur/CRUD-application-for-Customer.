package com.Sunbase.Assignment.repository;

import com.Sunbase.Assignment.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> findByFirstName(String firstName);
    List<Customer> findByCity(String city);

    List<Customer> findByEmail(String email);

    List<Customer> findByPhone(String phone);
}
