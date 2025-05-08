package com.gridnine.testing.services.impl;

import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.FlightBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@DisplayName("Тесты FlightFilterServiceImpl.")
public class FlightFilterServiceImplTest {

    private FlightFilterServiceImpl flightFilterService;
    private List<Flight> testData;
    private LocalDateTime now;

    private static final Logger testLogger = Logger.getLogger(FlightFilterServiceImplTest.class.getSimpleName());

    /**
     * Инициализирую входные, данные изначально содержащие некорректные перелеты.
     *
     * @see FlightBuilder
     */
    @BeforeEach
    public void init() {
        now = LocalDateTime.now();
        flightFilterService = new FlightFilterServiceImpl();
        testData = FlightBuilder.createFlights();
    }

    @Test
    @DisplayName("Тест давно начатых рейсов. (Негативный кейс кейс)")
    public void excludeFlightsNowNegative() {
        List<Flight> flights = testData;
        Assertions.assertFalse(
                flights
                        .stream()
                        .allMatch(flight -> flight.getSegments().getFirst().getDepartureDate().isAfter(now))
        );
    }

    @Test
    @DisplayName("Тест давно начатых рейсов. (Позитивный кейс)")
    public void excludeFlightsNowPositive() {
        testLogger.info("data before excludeFlightsNow: " + testData + "\n");
        List<Flight> flights = flightFilterService.excludeFlightsNow(testData);
        testLogger.info("data after excludeFlightsNow: " + flights  + "\n");
        Assertions.assertTrue(
                flights
                        .stream()
                        .allMatch(flight -> flight.getSegments().getFirst().getDepartureDate().isAfter(now))
        );
    }

    @Test
    @DisplayName("Тест на сегменты с вылетом позже прибытия. (Негативный кейс)")
    public void excludeInvalidDepartureNegative() {
        List<Flight> flights = testData;
        Assertions.assertFalse(
                flights.stream().allMatch(flight -> flight
                        .getSegments()
                        .stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
        );
    }

    @Test
    @DisplayName("Тест на сегменты с вылетом позже прибытия. (Позитивный кейс)")
    public void excludeInvalidDeparturePositive() {
        testLogger.info("data before excludeInvalidDeparture: " + testData + "\n");
        List<Flight> flights = flightFilterService.excludeInvalidDeparture(testData);
        testLogger.info("data after excludeInvalidDeparture: " + flights + "\n");
        Assertions.assertTrue(
                flights.stream().allMatch(flight -> flight
                        .getSegments()
                        .stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
        );
    }

    @Test
    @DisplayName("Тест рейсов с временем ожидания больше 2 часов. ")
    public void excludeFinalExpectationMoreThanNegative(){
        List<Flight> flights = flightFilterService.excludeFinalExpectationMoreThan(testData, 120);
        Assertions.assertEquals(4, flights.size());
    }

    @Test
    @DisplayName("Итоговый тест FlightFilterServiceImpl.")
    public void excludeFinal(){
        testLogger.info("data before excludeFinal: " + testData + "\n");
        List<Flight> flights = testData;
        flights = flightFilterService.excludeFlightsNow(flights);
        flights = flightFilterService.excludeInvalidDeparture(flights);
        flights = flightFilterService.excludeFinalExpectationMoreThan(flights, 120);
        testLogger.info("data after excludeFinal: " + flights + "\n");
        Assertions.assertEquals(2, flights.size());
    }


}
