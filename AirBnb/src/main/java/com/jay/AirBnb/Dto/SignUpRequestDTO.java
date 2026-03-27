package com.jay.AirBnb.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequestDTO {
    private String name;
    private String email;
    private String password;
}
