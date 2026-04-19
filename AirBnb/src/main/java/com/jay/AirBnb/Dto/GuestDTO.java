package com.jay.AirBnb.Dto;

import com.jay.AirBnb.Enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class GuestDTO {
    private Long id;
    private String name;
    private int age;
    private Gender gender;
}
