package com.jpmc.theater.discounts;

import com.jpmc.theater.Movie;
import com.jpmc.theater.Showing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TwentyPercentOffTest {
    private TwentyPercentOff discount;

    @BeforeEach
    public void initialize() {
        discount = new TwentyPercentOff();
    }

    @Test
    public void testIsApplicable_hasCode_true() {
        Showing showing = Mockito.mock(Showing.class);
        Movie movie = Mockito.mock(Movie.class);
        when(movie.getSpecialCode()).thenReturn(Discount.Code.TWENTY_PERCENT_OFF);
        when(showing.getMovie()).thenReturn(movie);
        assertTrue(discount.isApplicable(showing));
    }

    @Test
    public void testIsApplicable_noCode_false() {
        Showing showing = Mockito.mock(Showing.class);
        Movie movie = Mockito.mock(Movie.class);
        when(movie.getSpecialCode()).thenReturn(null);
        when(showing.getMovie()).thenReturn(movie);
        assertFalse(discount.isApplicable(showing));
    }

    @Test
    public void testGetDollarDiscountPerTicket_twentyPercent_success() {
        Showing showing = Mockito.mock(Showing.class);
        when(showing.getMovieFee()).thenReturn(100.0);
        assertEquals(20.0, discount.getDollarDiscountPerTicket(showing));
    }
}
