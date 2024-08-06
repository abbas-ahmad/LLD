package demos.eventcalendar;

import demos.eventcalendar.model.Event;
import demos.eventcalendar.model.User;
import demos.eventcalendar.observer.EmailReminderObserver;
import demos.eventcalendar.singleton.ReminderManager;
import demos.eventcalendar.state.EventStateContext;
import demos.eventcalendar.state.OngoingState;
import demos.eventcalendar.strategy.KeywordSearchStrategy;

import java.time.LocalDateTime;

public class EventCalendarApp {
    public static void main(String[] args) {
        // Create users
        User user1 = new User("user1", "john.doe", "john.doe@example.com");
        User user2 = new User("user2", "alice.smith", "alice.smith@example.com");

        // Create event objects
        Event event1 = new Event("event1", user1, "Birthday Party",
                LocalDateTime.of(2023, 7, 15, 18, 0),
                "Home", "Join us to celebrate John's birthday!");
        Event event2 = new Event("event2", user2, "Team Meeting",
                LocalDateTime.of(2023, 7, 20, 14, 30),
                "Office", "Discuss project updates.");


        // Create reminder manager
        EventManager eventManager = new EventManager();
        ReminderManager reminderManager = ReminderManager.getInstance();

        reminderManager.addReminder(event1, new EmailReminderObserver());
        reminderManager.addReminder(event2, new EmailReminderObserver());


        // Create search manager
        SearchManager searchManager = new SearchManager(new KeywordSearchStrategy());
        searchManager.searchEvents(eventManager.getEvents(), "birthday");

        // Set event state and handle actions
        EventStateContext context = new EventStateContext();
        context.setEventState(new OngoingState());
        context.handleEvent(event1);
    }
}
