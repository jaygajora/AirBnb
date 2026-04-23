package com.jay.AirBnb.Strategy;

import com.jay.AirBnb.Entity.InventoryEntity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(InventoryEntity inventoryEntity){
        BigDecimal price = wrapped.calculatePrice(inventoryEntity);

        LocalDate bookingDate = inventoryEntity.getDate();
        LocalDate today = LocalDate.now();


        //if the booking date is within the next 7 days
        if(!bookingDate.isBefore(today) && bookingDate.isBefore(today.plusDays(7))){
            price = price.multiply(BigDecimal.valueOf(1.15));
        }

        return price;
    }


}
