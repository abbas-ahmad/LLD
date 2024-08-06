package demos.eventcalendar;

import demos.eventcalendar.model.Event;
import demos.eventcalendar.strategy.EventSearchStrategy;

import java.util.List;

public class SearchManager {
    private final EventSearchStrategy searchStrategy;

    public SearchManager(EventSearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public List<Event> searchEvents(List<Event> events, String keyword){
        return searchStrategy.searchEvents(events, keyword);
    }
}
