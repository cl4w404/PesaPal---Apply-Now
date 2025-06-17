package com.devcorp.bank_proj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private String gender;
    private String address;;
    private String stateOfOrigin;
    private String dateOfBirth;
    private String phoneNumber;
    private String alternativePhone;
    private String status;
}
