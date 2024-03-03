package cz.uhk.fim.workshop.dtoOut;

import cz.uhk.fim.workshop.model.Event;
import cz.uhk.fim.workshop.model.User;

import java.time.LocalTime;
import java.util.Date;

public class EventDtoOut extends Event {
    private Integer id;

    public EventDtoOut() {
    }

    public EventDtoOut(Integer id, String name, String description, Date date, LocalTime time, Boolean publicEvent, User user) {
        super(id, name, description, date, time, publicEvent, user);
    }
}
