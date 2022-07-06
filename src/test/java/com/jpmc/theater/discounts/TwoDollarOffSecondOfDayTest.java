package com.jpmc.theater.discounts;

import com.jpmc.theater.Showing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TwoDollarOffSecondOfDayTest {

    private TwoDollarOffSecondOfDay discount;

    @BeforeEach
    public void initialize() {
        discount = new TwoDollarOffSecondOfDay();
    }

    @Test
    public void testIsApplicable_secondOfDay_true() {
        Showing showing = Mockito.mock(Showing.class);
        when(showing.getSequenceOfTheDay()).thenReturn(2);
        assertTrue(discount.isApplicable(showing));
    }

    @Test
    public void testIsApplicable_notSecondOfDay_false() {
        Showing showing = Mockito.mock(Showing.class);
        when(showing.getSequenceOfTheDay()).thenReturn(7);
        assertFalse(discount.isApplicable(showing));
    }

    @Test
    public void testGetDollarDiscountPerTicket_twoDollars_success() {
        Showing showing = Mockito.mock(Showing.class);
        assertEquals(2.0, discount.getDollarDiscountPerTicket(showing));
    }
}
