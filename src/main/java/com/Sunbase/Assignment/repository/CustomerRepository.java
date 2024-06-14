package com.Sunbase.Assignment.repository;

import com.Sunbase.Assignment.model.Customer;
import com.Sunbase.Assignment.service.CustomerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query("SELECT c FROM Customer c WHERE c.first_name = :first_name")
    List<Customer> findByFirst_name(String first_name);
    List<Customer> findByCity(String city);

    List<Customer> findByEmail(String email);

    List<Customer> findByPhone(String phone);

    Optional<Customer> findByUuid(String uuid);
}
