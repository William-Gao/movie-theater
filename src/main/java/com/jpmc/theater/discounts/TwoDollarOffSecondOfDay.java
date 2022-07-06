package com.jpmc.theater.discounts;

import com.jpmc.theater.Showing;

public class TwoDollarOffSecondOfDay implements Discount {
    @Override
    public boolean isApplicable(Showing showing) {
        return showing.getSequenceOfTheDay() == 2;
    }

    @Override
    public double getDollarDiscountPerTicket(Showing showing) {
        return 2.0;
    }
}
