package com.gridnine.testing.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Утилитарный класс, формирующий базовый пример состояния списка перелетов.
 */
public final class FlightBuilder {

    public static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
                // ru:   Обычный рейс с двухчасовой продолжительностью.
                // eng:  A normal flight with two hour duration.
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                // ru:  Обычный рейс с несколькими сегментами.
                // eng: A normal multi segment flight.
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                // ru:  Рейс с вылетом в прошлом.
                // eng: A flight departing in the past.
            createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                // ru:  Рейс, который вылетает раньше, чем прилетает.
                // eng: A flight that departs before it arrives.
            createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                // ru:  Рейс с более чем двухчасовой стоянкой между сегментами.
                // eng: A flight with more than two hours ground time.
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                // ru: Еще один рейс с более чем двухчасовой стоянкой между сегментами.
                // eng: Another flight with more than two hours ground time.
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }

}