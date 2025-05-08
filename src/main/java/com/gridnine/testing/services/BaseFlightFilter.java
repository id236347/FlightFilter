package com.gridnine.testing.services;

import com.gridnine.testing.models.Flight;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Danil Gorev </br>
 * Поведение базового фильтра.
 * Выношу в отдельный интерфейс соблюдая принцип разделения.
 */
public interface BaseFlightFilter {

    /**
     * Универсальная/стандартная реализация фильтра. </br>
     * Принимает {@link FlightPredicate} как будующие правило для фильтрации и возвращает результат на его основе.
     *
     * @param flights   перелеты.
     * @param predicate правило.
     * @return рейсы, которые удовлетворяют {@link FlightPredicate} правилу.
     * @see FlightPredicate
     * @see Flight
     * @see List
     */
    default List<Flight> filterFlights(List<Flight> flights, FlightPredicate predicate) {
        return flights.stream().filter(predicate::filter).collect(Collectors.toList());
    }

}
