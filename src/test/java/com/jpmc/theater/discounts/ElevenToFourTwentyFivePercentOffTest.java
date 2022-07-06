package com.jpmc.theater.discounts;

import com.jpmc.theater.Showing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ElevenToFourTwentyFivePercentOffTest {

    private ElevenToFourTwentyFivePercentOff discount;

    @BeforeEach
    public void initialize() {
        discount = new ElevenToFourTwentyFivePercentOff();
    }

    @Test
    public void testIsApplicable_timeBetweenElevenToFour_true() {
        Showing showing = Mockito.mock(Showing.class);
        when(showing.getShowStartTime()).thenReturn(LocalDateTime.of(2022, 12, 2, 13, 0));
        assertTrue(discount.isApplicable(showing));
    }

    @Test
    public void testIsApplicable_timeNotBetweenElevenToFour_false() {
        Showing showing = Mockito.mock(Showing.class);
        when(showing.getShowStartTime()).thenReturn(LocalDateTime.of(2022, 12, 2, 19, 0));
        assertFalse(discount.isApplicable(showing));
    }

    @Test
    public void testGetDollarDiscountPerTicket_twentyFivePercentOff_success() {
        Showing showing = Mockito.mock(Showing.class);
        when(showing.getShowStartTime()).thenReturn(LocalDateTime.of(2022, 12, 2, 19, 0));
        when(showing.getMovieFee()).thenReturn(100.0);
        assertEquals(25.0, discount.getDollarDiscountPerTicket(showing));
    }



}
