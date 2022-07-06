package com.jpmc.theater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.theater.discounts.*;
import com.jpmc.theater.utils.DurationUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class Theater {
    private LocalDateProvider provider;
    private List<Showing> schedule;
    private List<Discount> discounts;

    public Theater(LocalDateProvider provider, List<Showing> schedule, List<Discount> discounts) {
        this.provider = provider;
        this.schedule = schedule;
        this.discounts = discounts;
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        if ((1 <= sequence) && (sequence <= schedule.size())) {
            Showing showing = schedule.get(sequence - 1);
            Double price = showing.getMovieFee();
            Optional<Double> dollarDiscount = Optional.empty();

            if (this.discounts != null) {
                dollarDiscount = this.discounts.stream()
                        .filter(discount -> discount.isApplicable(showing))
                        .map(discount -> discount.getDollarDiscountPerTicket(showing))
                        .max(Double::compare);
            }

            if (dollarDiscount.isPresent()) {
                price = price - dollarDiscount.get();
            }

            return Reservation.builder()
                    .customer(customer)
                    .showing(showing)
                    .audienceCount(howManyTickets)
                    .totalFee(price * howManyTickets)
                    .build();
        } else {
            throw new IllegalArgumentException("not able to find any showing for given sequence " + sequence);
        }
    }

    public void printSchedule() {
        System.out.println(provider.currentDate());
        System.out.println("===================================================");
        schedule.forEach(s ->
                System.out.println(s.getSequenceOfTheDay()
                        + ": " + s.getShowStartTime()
                        + " " + s.getMovie().getTitle()
                        + " " + DurationUtil.humanReadableFormat(s.getMovie().getRunningTime())
                        + " $" + s.getMovieFee())
        );
        System.out.println("===================================================");
    }

    public void printScheduleJson() {
        final Map<Integer, Showing> sequenceToShowing = this.schedule.stream().collect(Collectors.toMap(
                s -> s.getSequenceOfTheDay(),
                s -> s
        ));
        try {
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter()
                    .writeValueAsString(sequenceToShowing));
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException("Exception in printing schedule in JSON format : ", e);
        }
    }

    public static void main(String[] args) {

        LocalDateProvider provider = LocalDateProvider.singleton();
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, Discount.Code.TWENTY_PERCENT_OFF);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9);

        List<Discount> discounts = List.of(
            new ElevenToFourTwentyFivePercentOff(),
            new OneDollarOffSeventhOfDay(),
            new ThreeDollarOffFirstOfDay(),
            new TwentyPercentOff(),
            new TwoDollarOffSecondOfDay());

        List<Showing> schedule = List.of(
                new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
                new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
                new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
                new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
                new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
                new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
                new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
                new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
                new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0))));
        Theater theater = new Theater(provider, schedule, discounts);
        theater.printSchedule();
    }
}
