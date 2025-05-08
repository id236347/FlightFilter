package com.gridnine.testing.services;

import com.gridnine.testing.models.Flight;

/**
 * @author Danil Gorev </br>
 * Функциональный интерфейс для базового фильтра перелетов.
 */
@FunctionalInterface
public interface FlightPredicate {
    boolean filter(Flight flight);
}
