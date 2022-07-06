package com.jpmc.theater.discounts;

import com.jpmc.theater.Showing;

public class TwentyPercentOff implements Discount {

    @Override
    public boolean isApplicable(Showing showing) {
        return Code.TWENTY_PERCENT_OFF.equals(showing.getMovie().getSpecialCode());
    }

    @Override
    public double getDollarDiscountPerTicket(Showing showing) {
        // need to change this to get a discount code
        return showing.getMovieFee() * 0.2;
    }
}
