package com.gridnine.testing.main;


import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.FlightBuilder;
import com.gridnine.testing.services.impl.FlightFilterServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FlightFilterServiceImpl flightFilterService = new FlightFilterServiceImpl();
        List<Flight> flightsBeforeFiltering = FlightBuilder.createFlights();
        System.out.println("Before filtering: " + flightsBeforeFiltering);
        flightsBeforeFiltering = flightFilterService.excludeFlightsNow(flightsBeforeFiltering);
        flightsBeforeFiltering = flightFilterService.excludeFinalExpectationMoreThan(flightsBeforeFiltering, 120);
        flightsBeforeFiltering = flightFilterService.excludeInvalidDeparture(flightsBeforeFiltering);
        System.out.println("After filtering: " + flightsBeforeFiltering);
    }
}