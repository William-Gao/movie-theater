package com.jpmc.theater.discounts;

import com.jpmc.theater.Showing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OneDollarOffSeventhOfDayTest {
    private OneDollarOffSeventhOfDay discount;

    @BeforeEach
    public void initialize() {
        discount = new OneDollarOffSeventhOfDay();
    }

    @Test
    public void testIsApplicable_seventhOfDay_true() {
        Showing showing = Mockito.mock(Showing.class);
        when(showing.getSequenceOfTheDay()).thenReturn(7);
        assertTrue(discount.isApplicable(showing));
    }

    @Test
    public void testIsApplicable_nonSeventhOfDay_false() {
        Showing showing = Mockito.mock(Showing.class);
        when(showing.getSequenceOfTheDay()).thenReturn(9);
        assertFalse(discount.isApplicable(showing));
    }

    @Test
    public void testGetDollarDiscountPerTicket_oneDollar_success() {
        Showing showing = Mockito.mock(Showing.class);
        assertEquals(1.0, discount.getDollarDiscountPerTicket(showing));
    }
}
