package com.gridnine.testing.services.impl;

import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.Segment;
import com.gridnine.testing.services.FlightFilterService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Danil Gorev </br>
 * Сервисный слой фильтра.
 * Тут можно было бы повесить @Service и внедрить @Repository класс.
 */
public class FlightFilterServiceImpl implements FlightFilterService {

    /**
     * Метод исключающий рейсы, у которых вылет до текущего момента времени.
     *
     * @param flights список рейсов.
     * @return рейсы, у которых вылет НЕ до текущего момента времени.
     * @see Flight
     * @see Segment
     * @see List
     */
    @Override
    public List<Flight> excludeFlightsNow(List<Flight> flights) {
        // Прохожусь по перелетам ->
        // Дергаю сегменты каждого перелета ->
        // Если есть сегмент с временем до нынешнего, то пропускаю целый перелет.
        LocalDateTime now = LocalDateTime.now();
        return filterFlights(flights, flight -> flight.getSegments().stream()
                .noneMatch(segment -> segment.getDepartureDate().isBefore(now)));
    }

    /**
     * Метод исключающий рейсы, где есть сегмент, у которого прибытие раньше вылета.
     *
     * @param flights список рейсов.
     * @return список рейсов, которые не имеют сегменты с прибытием раньше вылета.
     * @see Flight
     * @see Segment
     * @see List
     */
    @Override
    public List<Flight> excludeInvalidDeparture(List<Flight> flights) {
        // Прохожусь по перелетам ->
        // Дергаю сегменты каждого перелета ->
        // Если есть сегмент с прибытием после вылета, то пропускаю целый перелет.
        return filterFlights(flights, flight ->
                flight.getSegments().stream()
                        .noneMatch(segment ->
                                segment.getArrivalDate().isBefore(segment.getDepartureDate())
                        )
        );
    }

    /**
     * Метод исключающий рейсы, где общее время ожидания на земле (между сегментами) превышает подаваемое время в минутах.
     *
     * @param flights      список рейсов.
     * @param minOfWaiting максимальное допустимое время ожидания на земле. (в минутах)
     * @return список рейсов, где общее время ожидания на земле (между сегментами) НЕ превышает подаваемое время в минутах.
     * @see Flight
     * @see Segment
     * @see List
     */
    @Override
    public List<Flight> excludeFinalExpectationMoreThan(List<Flight> flights, int minOfWaiting) {
        return filterFlights(flights, flight -> {
                    var segments = flight.getSegments();
                    // В таком случае ожидания на земле нет.
                    if (segments.size() <= 1)
                        return true;
                    // Счетчик общего времени ожидания между сегментами.
                    int totalWaitingMinutes = 0;
                    for (int i = 0; i < segments.size() - 1; i++) {
                        // Текущий сегмент.
                        Segment current = segments.get(i);
                        // Следующий сегмент.
                        Segment next = segments.get(i + 1);
                        // Ожидание на земле между сегментами.
                        int waitingMinutes = (int) java.time.Duration.between(
                                current.getArrivalDate(),
                                next.getDepartureDate()
                        ).toMinutes();
                        // Обогащаю счетчик.
                        totalWaitingMinutes += waitingMinutes;
                    }
                    // Сравниваю суммарное время между сегментами с максимально допустимым.
                    return totalWaitingMinutes <= minOfWaiting;
                }
        );
    }


}
