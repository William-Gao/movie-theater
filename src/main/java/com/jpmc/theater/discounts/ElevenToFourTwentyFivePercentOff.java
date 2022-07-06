package com.jpmc.theater.discounts;

import com.jpmc.theater.Showing;

public class ElevenToFourTwentyFivePercentOff implements Discount {
    @Override
    public boolean isApplicable(Showing showing) {
        Integer hour = showing.getShowStartTime().getHour();

        return hour >= 11 && hour <= 16;
    }

    @Override
    public double getDollarDiscountPerTicket(Showing showing) {
        return showing.getMovieFee() * 0.25;
    }
}
