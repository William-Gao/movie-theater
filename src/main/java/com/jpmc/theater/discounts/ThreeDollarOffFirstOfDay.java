package com.jpmc.theater.discounts;

import com.jpmc.theater.Showing;

public class ThreeDollarOffFirstOfDay implements Discount {

    @Override
    public boolean isApplicable(Showing showing) {
        return showing.getSequenceOfTheDay() == 1;
    }

    @Override
    public double getDollarDiscountPerTicket(Showing showing) {
        return 3.0;
    }
}
