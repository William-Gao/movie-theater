package com.jpmc.theater.discounts;

import com.jpmc.theater.Showing;

public class OneDollarOffSeventhOfDay implements Discount {
    @Override
    public boolean isApplicable(Showing showing) {
        return showing.getSequenceOfTheDay() == 7;
    }

    @Override
    public double getDollarDiscountPerTicket(Showing showing) {
        return 1.0;
    }
}
