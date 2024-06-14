package com.Sunbase.Assignment.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


//Customer data. Get stored to customer table in database
@Entity
@Data
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String uuid;

    //Camel case is avoided to get it in sync with the remote data provided
    String first_name;

    String last_name;

    String street;

    String address;

    String city;

    String state;

    String email;

    String phone;


}
