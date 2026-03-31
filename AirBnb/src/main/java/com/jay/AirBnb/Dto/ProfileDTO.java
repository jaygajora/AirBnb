package com.jay.AirBnb.Dto;

import com.jay.AirBnb.Enums.Gender;
import com.jay.AirBnb.Enums.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class ProfileDTO {
    private String fullName;
    private String email;
    private Gender gender;
    private Set<Role> roles;
    private LocalDate dateOfBirth;
}
