package demos.eventcalendar.state;

import demos.eventcalendar.model.Event;

public class EventStateContext {
    private EventState eventState;

    public EventStateContext() {
        this.eventState = new CreateState();
    }

    public void setEventState(EventState eventState){
        this.eventState = eventState;
    }

    public void handleEvent(Event event){
        eventState.handleEvent(event);
    }


}
