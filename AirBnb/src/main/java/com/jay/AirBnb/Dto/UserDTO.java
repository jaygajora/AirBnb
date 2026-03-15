package com.jay.AirBnb.Dto;

import com.jay.AirBnb.Enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String fullName;
    private Gender gender;
    private LocalDate dateOfBirth;
}
