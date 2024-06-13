package com.Sunbase.Assignment.service;

import com.Sunbase.Assignment.model.Customer;
import com.Sunbase.Assignment.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    public CustomerRepository customerRepository;

    public String addCustomer(Customer customer){
        customerRepository.save(customer);
        return "Customer added successfully";
    }

    public String updateCustomer(Customer customer){
        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        savedCustomer.setFirstName(customer.getFirstName());
        savedCustomer.setLastName(customer.getLastName());
        savedCustomer.setStreet(customer.getStreet());
        savedCustomer.setAddress(customer.getAddress());
        savedCustomer.setCity(customer.getCity());
        savedCustomer.setState(customer.getState());
        savedCustomer.setEmail(customer.getEmail());
        savedCustomer.setPhone(customer.getPhone());

        customerRepository.save(savedCustomer);

        return "Customer updated Successfully";

    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int id){
        return customerRepository.findById(id).get();
    }

    public String deleteCustomerById(int id){
        customerRepository.deleteById(id);
        return "Customer Successfully Deleted";
    }

    public Page<Customer> getAllCustomersWithPaginationAndSort(int offset, int pageSize,String field){
        Page<Customer> customerList = customerRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return customerList;
    }

    public List<Customer> getCustomerByFirstName(String firstName){
        return customerRepository.findByFirstName(firstName);
    }

    public List<Customer> getCustomerByCity(String city){
        return customerRepository.findByCity(city);
    }

    public List<Customer> getCustomerByEmail(String email){
        return customerRepository.findByEmail(email);
    }

    public List<Customer> getCustomerByPhone(String phone){
        return customerRepository.findByPhone(phone);
    }
}
