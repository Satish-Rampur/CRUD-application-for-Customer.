package com.Sunbase.Assignment.dto.resoponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponseDto {
    String first_name;
    String last_name;
    String street;
    String address;
    String city;
    String state;
    String email;
    String phone;
}
