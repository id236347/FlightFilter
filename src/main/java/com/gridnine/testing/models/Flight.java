package com.gridnine.testing.models;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Модель перелета.
 */
public class Flight {

    /**
     * Список перелетов.
     */
    private final List<Segment> segments;

    public Flight(final List<Segment> segs) {
        segments = segs;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }

}