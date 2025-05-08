package com.gridnine.testing.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Перелет.
 */
public class Segment {

    /**
     * Дата и время вылета.
     */
    private final LocalDateTime departureDate;

    /**
     * Дата и время прилета.
     */
    private final LocalDateTime arrivalDate;

    Segment(final LocalDateTime dep, final LocalDateTime arr) {
        departureDate = Objects.requireNonNull(dep);
        arrivalDate = Objects.requireNonNull(arr);
    }

    /**
     * Возвращает дату и время вылета.
     * @return дата и время вылета.
     */
    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    /**
     * Возвращает дату и время прилета.
     * @return дата и время прилета.
     */
    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String toString() {

        DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return '[' + departureDate.format(fmt) + '|' + arrivalDate.format(fmt)
            + ']';
    }
}