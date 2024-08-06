package demos.eventcalendar.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventId;
    private User creator;
    private List<User> attendees;
    private String title;
    private LocalDateTime dateTime;
    private String location;
    private String description;
    private boolean isRecurring;

    public Event(String eventId, User creator, String title, LocalDateTime dateTime,
                 String location, String description) {
        this.eventId = eventId;
        this.creator = creator;
        this.attendees = new ArrayList<>();
        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
        this.description = description;
        this.isRecurring = false;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<User> attendees) {
        this.attendees = attendees;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }
}
