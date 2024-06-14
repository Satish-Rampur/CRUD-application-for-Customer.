package com.Sunbase.Assignment.controller;

import com.Sunbase.Assignment.model.Customer;
import com.Sunbase.Assignment.service.AdminDetailsService;
import com.Sunbase.Assignment.service.CustomerService;
import com.Sunbase.Assignment.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CustomerController {

    @Autowired
    public CustomerService customerService;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AdminDetailsService adminDetailsService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity addCustomer(@RequestBody Customer customer){
        String message = customerService.addCustomer(customer);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity updateCustomer(@RequestBody Customer customer){
        String message = customerService.updateCustomer(customer);
        return new ResponseEntity<>(message,HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity getAll(){
        List<Customer> customerList = customerService.getAllCustomers();
        return new ResponseEntity<>(customerList,HttpStatus.ACCEPTED);
    }

    @GetMapping("/get/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity getById(@PathVariable("customerId") int customerId){
        Customer customer = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(customer,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity deleteById(@PathVariable("customerId") int customerId){
        String message = customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(message,HttpStatus.ACCEPTED);
    }

    @GetMapping("/allWithPagination/{offset}/{pageSize}/{field}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity getAllCustomerWithPaginationAndSort(@PathVariable("offset") int offset, @PathVariable("pageSize") int pageSize, @PathVariable("field") String field){

        Page<Customer> customerList = customerService.getAllCustomersWithPaginationAndSort(offset, pageSize,field);
        return new ResponseEntity<>(customerList,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getByFirstName/{firstName}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity getCustomerByFirstName(@PathVariable("firstName") String firstName){
        List<Customer> customerList = customerService.getCustomerByFirstName(firstName);
        return new ResponseEntity<>(customerList,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getByCity/{city}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity getCustomerByCity(@PathVariable("city") String city){
        List<Customer> customerList = customerService.getCustomerByCity(city);
        return new ResponseEntity<>(customerList,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getByEmail/{email}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity getCustomerByEmail(@PathVariable("email") String email){
        List<Customer> customerList = customerService.getCustomerByCity(email);
        return new ResponseEntity<>(customerList,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getByPhone/{phone}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity getCustomerByPhone(@PathVariable("phone") String phone){
        List<Customer> customerList = customerService.getCustomerByPhone(phone);
        return new ResponseEntity<>(customerList,HttpStatus.ACCEPTED);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/sync")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity syncCustomers(){
        customerService.syncCustomers();
        return new ResponseEntity<>("Customers Synchronised successfully",HttpStatus.OK);
    }




}
