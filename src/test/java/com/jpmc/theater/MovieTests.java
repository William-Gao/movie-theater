package com.jpmc.theater;

import com.jpmc.theater.discounts.Discount;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MovieTests {
    @Test
    public void testEquals_same_true() {
        final Movie spiderManOne = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, Discount.Code.TWENTY_PERCENT_OFF);
        final Movie spiderManTwo = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, Discount.Code.TWENTY_PERCENT_OFF);

        assertEquals(spiderManOne, spiderManTwo);
    }

    @Test
    public void testEquals_differentTitle_false() {
        final Movie spiderManOne = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, Discount.Code.TWENTY_PERCENT_OFF);
        final Movie spiderManTwo = new Movie("Spider-Man", Duration.ofMinutes(90),12.5, Discount.Code.TWENTY_PERCENT_OFF);

        assertNotEquals(spiderManOne, spiderManTwo);
    }

    @Test
    public void testEquals_differentDuration_false() {
        final Movie spiderManOne = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(100),12.5, Discount.Code.TWENTY_PERCENT_OFF);
        final Movie spiderManTwo = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(5),12.5, Discount.Code.TWENTY_PERCENT_OFF);
    }

    @Test
    public void testEquals_differentPrice_false() {
        final Movie spiderManOne = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),100, Discount.Code.TWENTY_PERCENT_OFF);
        final Movie spiderManTwo = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),5, Discount.Code.TWENTY_PERCENT_OFF);
    }

    @Test
    public void testEquals_differentDiscountCode_false() {
        final Movie spiderManOne = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, Discount.Code.TWENTY_PERCENT_OFF);
        final Movie spiderManTwo = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, null);
    }
}
