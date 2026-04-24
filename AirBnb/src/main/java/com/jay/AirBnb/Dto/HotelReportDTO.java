package com.jay.AirBnb.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelReportDTO {
    private Long totalConfirmedBookings;
    private BigDecimal totalRevenueOfConfirmedBookings;
    private BigDecimal averageRevenue;
}
