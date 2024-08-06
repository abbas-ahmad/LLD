package demos.eventcalendar.singleton;

import demos.eventcalendar.model.Event;
import demos.eventcalendar.observer.ReminderObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReminderManager {
    private static ReminderManager instance;
    private final Map<Event, List<ReminderObserver>> observers;

    private ReminderManager(){
        observers = new HashMap<>();
    }

    public static ReminderManager getInstance(){
        if (instance == null){
            synchronized (ReminderManager.class){
                if(instance == null){
                    instance = new ReminderManager();
                }
            }
        }
        return instance;
    }

    public void addReminder(Event event, ReminderObserver observer){
        observers.computeIfAbsent(event, k -> new ArrayList<>()).add(observer);
    }

    public void removeReminder(Event event, ReminderObserver observer){
        List<ReminderObserver> eventObservers = observers.get(event);

        if(eventObservers != null){
            eventObservers.remove(observer);
        }
    }

    public void notifyObservers(Event event){
        List<ReminderObserver> reminderObservers = observers.get(event);

        for(ReminderObserver observer : reminderObservers){
            observer.update(event);
        }
    }
}
