package com.gridnine.testing.services;

import com.gridnine.testing.models.Flight;

import java.util.List;

/**
 * @author Danil Gorev </br>
 * Базовый контракт фильтра.
 */
public interface FlightFilterService extends BaseFlightFilter {

    /**
     * Метод исключающий рейсы, у которых вылет до текущего момента времени.
     *
     * @param flights список рейсов.
     * @return рейсы, у которых вылет НЕ до текущего момента времени.
     * @see Flight
     * @see List
     */
    List<Flight> excludeFlightsNow(List<Flight> flights);

    /**
     * Метод исключающий рейсы, где есть сегмент, у которого прибытие раньше вылета.
     *
     * @param flights список рейсов.
     * @return список рейсов, которые не имеют сегменты с прибытием раньше вылета.
     * @see Flight
     * @see List
     */
    List<Flight> excludeInvalidDeparture(List<Flight> flights);

    /**
     * Метод исключающий рейсы, где общее время ожидания на земле (между сегментами) превышает подаваемое время в минутах.
     * @param flights список рейсов.
     * @param minOfWaiting максимальное допустимое время ожидания на земле. (в минутах)
     * @return список рейсов, где общее время ожидания на земле (между сегментами) НЕ превышает подаваемое время в минутах.
     * @see Flight
     * @see List
     */
    List<Flight>  excludeFinalExpectationMoreThan(List<Flight> flights, int minOfWaiting);
}
