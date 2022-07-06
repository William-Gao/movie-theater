package com.jpmc.theater;

import com.jpmc.theater.discounts.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TheaterTests {
    LocalDateProvider provider = LocalDateProvider.singleton();

    @Mock
    private Customer customer;

    private Theater theater;

    @BeforeEach
    void initialize()
    {
        this.theater = new Theater(LocalDateProvider.singleton(), initializeMockShowings(), null);
    }

    @Test
    void testReserve_invalidSequence_throwsException() {
        assertThrows(Exception.class, () -> theater.reserve(customer, 10000, 10));
    }

    @Test
    void testReserve_valid_success() {
        Movie mockTurningRed = Mockito.mock(Movie.class);
        when(mockTurningRed.getBasePrice()).thenReturn(11.0);
        when(mockTurningRed.getRunningTime()).thenReturn(Duration.ofMinutes(85));
        when(mockTurningRed.getSpecialCode()).thenReturn(null);

        Showing mockShowing = Mockito.mock(Showing.class);

        when(mockShowing.getSequenceOfTheDay()).thenReturn(1);
        when(mockShowing.getMovie()).thenReturn(mockTurningRed);
        when(mockShowing.getMovieFee()).thenReturn(11.0);
        when(mockShowing.getShowStartTime()).thenReturn(LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0)));
        this.theater = new Theater(provider, List.of(mockShowing), null);

        Reservation reservation = theater.reserve(customer, 1, 1);
        assertEquals(1, reservation.getAudienceCount());
        assertEquals(customer, reservation.getCustomer());
        assertEquals(mockShowing, reservation.getShowing());
        assertEquals(11.0, reservation.getTotalFee());
    }

    @Test
    void testReserve_discountsNull_useMovieBasePrice() {
        this.theater = new Theater(provider, initializeMockShowings(), null);

        Reservation reservation = theater.reserve(customer, 2, 10);
        assertEquals(125, reservation.getTotalFee());

    }

    @Test
    void testReserve_discountsEmpty_useMovieBasePrice() {
        this.theater = new Theater(provider, initializeMockShowings(), List.of());

        Reservation reservation = theater.reserve(customer, 2, 10);
        assertEquals(125, reservation.getTotalFee());
    }

    @Test
    void testReserve_noDiscountsApplicable_useMovieBasePrice() {
        Discount mockDiscount1 = Mockito.mock(Discount.class);
        Discount mockDiscount2 = Mockito.mock(Discount.class);

        when(mockDiscount1.isApplicable(any())).thenReturn(false);
        when(mockDiscount2.isApplicable(any())).thenReturn(false);

        this.theater = new Theater(provider, initializeMockShowings(), List.of(mockDiscount1, mockDiscount2));

        Reservation reservation = theater.reserve(customer, 2, 10);
        assertEquals(125, reservation.getTotalFee());
    }

    @Test
    void testReserve_hasDiscounts_useLargestDiscount() {
        Discount mockDiscount1 = Mockito.mock(Discount.class);
        Discount mockDiscount2 = Mockito.mock(Discount.class);

        when(mockDiscount1.isApplicable(any())).thenReturn(true);
        when(mockDiscount1.getDollarDiscountPerTicket(any())).thenReturn(1.0);
        when(mockDiscount2.isApplicable(any())).thenReturn(true);
        when(mockDiscount1.getDollarDiscountPerTicket(any())).thenReturn(5.0);

        this.theater = new Theater(provider, initializeMockShowings(), List.of(mockDiscount1, mockDiscount2));

        Reservation reservation = theater.reserve(customer, 2, 10);
        assertEquals(75, reservation.getTotalFee());
    }

    @Test
    void test_printSchedule_success() {
        theater.printSchedule();
    }

    @Test
    void test_printScheduleJson_success() {
        Showing s1 = new Showing(new Movie("m2", Duration.ofMinutes(90),
                10.0, Discount.Code.TWENTY_PERCENT_OFF),
                1,
                LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0)));
        Showing s2 = new Showing(new Movie("m2", Duration.ofMinutes(70),
                19.0),
                2,
                LocalDateTime.of(provider.currentDate(), LocalTime.of(10, 0)));
        this.theater = new Theater(provider, List.of(s1, s2), null);
        theater.printScheduleJson();
    }

    private List<Showing> initializeMockShowings() {

        Movie mockTurningRed = Mockito.mock(Movie.class);
        Movie mockSpiderMan = Mockito.mock(Movie.class);
        Movie mockBatMan = Mockito.mock(Movie.class);

        when(mockTurningRed.getBasePrice()).thenReturn(11.0);
        when(mockTurningRed.getRunningTime()).thenReturn(Duration.ofMinutes(85));
        when(mockTurningRed.getSpecialCode()).thenReturn(null);

        when(mockSpiderMan.getBasePrice()).thenReturn(12.5);
        when(mockSpiderMan.getRunningTime()).thenReturn(Duration.ofMinutes(90));
        when(mockSpiderMan.getSpecialCode()).thenReturn(Discount.Code.TWENTY_PERCENT_OFF);

        when(mockBatMan.getBasePrice()).thenReturn(9.0);
        when(mockBatMan.getRunningTime()).thenReturn(Duration.ofMinutes(95));
        when(mockBatMan.getSpecialCode()).thenReturn(null);

        Showing mockShowing1 = Mockito.mock(Showing.class);
        Showing mockShowing2 = Mockito.mock(Showing.class);
        Showing mockShowing3 = Mockito.mock(Showing.class);

        when(mockShowing1.getSequenceOfTheDay()).thenReturn(1);
        when(mockShowing1.getMovie()).thenReturn(mockTurningRed);
        when(mockShowing1.getMovieFee()).thenReturn(11.0);
        when(mockShowing1.getShowStartTime()).thenReturn(LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0)));

        when(mockShowing2.getSequenceOfTheDay()).thenReturn(2);
        when(mockShowing2.getMovie()).thenReturn(mockSpiderMan);
        when(mockShowing2.getMovieFee()).thenReturn(12.5);
        when(mockShowing2.getShowStartTime()).thenReturn(LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0)));

        when(mockShowing3.getSequenceOfTheDay()).thenReturn(3);
        when(mockShowing3.getMovie()).thenReturn(mockTurningRed);
        when(mockShowing3.getMovieFee()).thenReturn(9.0);
        when(mockShowing3.getShowStartTime()).thenReturn(LocalDateTime.of(provider.currentDate(), LocalTime.of(12,50)));

        return List.of(mockShowing1, mockShowing2, mockShowing3);
    }
}
