package com.jpmc.theater.discounts;

import com.jpmc.theater.Showing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ThreeDollarOffFirstOfDayTest {

    private ThreeDollarOffFirstOfDay discount;

    @BeforeEach
    public void initialize() {
        discount = new ThreeDollarOffFirstOfDay();
    }

    @Test
    public void testIsApplicable_firstOfDay_true() {
        Showing showing = Mockito.mock(Showing.class);
        when(showing.getSequenceOfTheDay()).thenReturn(1);
        assertTrue(discount.isApplicable(showing));
    }

    @Test
    public void testIsApplicable_notFirstOfDay_false() {
        Showing showing = Mockito.mock(Showing.class);
        when(showing.getSequenceOfTheDay()).thenReturn(9);
        assertFalse(discount.isApplicable(showing));
    }

    @Test
    public void testGetDollarDiscountPerTicket_threeDollars_success() {
        Showing showing = Mockito.mock(Showing.class);
        assertEquals(3.0, discount.getDollarDiscountPerTicket(showing));
    }
}
