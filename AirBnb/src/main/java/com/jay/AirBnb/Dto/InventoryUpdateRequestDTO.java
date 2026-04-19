package com.jay.AirBnb.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class InventoryUpdateRequestDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean closed;
    private BigDecimal surgeFactor;
}
