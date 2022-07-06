package com.jpmc.theater;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmc.theater.discounts.Discount;
import lombok.Getter;

import java.time.Duration;
import java.util.Objects;
@Getter
public class Movie {
    private String title;
    @JsonIgnore
    private Duration runningTime;
    @JsonProperty("price")
    private double basePrice;
    @JsonIgnore
    private Discount.Code specialCode;

    public Movie(String title, Duration runningTime, double basePrice, Discount.Code specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.basePrice = basePrice;
        this.specialCode = specialCode;
    }

    public Movie(String title, Duration runningTime, double basePrice) {
        this(title, runningTime, basePrice, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.basePrice, basePrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, runningTime, basePrice, specialCode);
    }
}