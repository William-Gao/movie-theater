package com.jpmc.theater;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Reservation {
    private Customer customer;
    private Showing showing;
    private int audienceCount;
    private double totalFee;
}