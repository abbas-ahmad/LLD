package demos.eventcalendar.strategy;

import demos.eventcalendar.model.Event;

import java.util.List;

public interface EventSearchStrategy {
    List<Event> searchEvents(List<Event> events, String keyword);
}
