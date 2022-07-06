package com.jpmc.theater.discounts;

import com.jpmc.theater.Showing;

import java.util.Map;

public interface Discount {

    public boolean isApplicable(Showing showing);

    public double getDollarDiscountPerTicket(Showing showing);

    public static enum Code {
        TWENTY_PERCENT_OFF
    }
}
