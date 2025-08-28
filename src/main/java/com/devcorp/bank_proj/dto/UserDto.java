package com.devcorp.bank_proj.dto;

import com.devcorp.bank_proj.models.Role;
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
    private String password;
    private String address;;
    private String stateOfOrigin;
    private String dateOfBirth;
    private String phoneNumber;
    private String alternativePhone;
    private String status;
    private Role role;
}
