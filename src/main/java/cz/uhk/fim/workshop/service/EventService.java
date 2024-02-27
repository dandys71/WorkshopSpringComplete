package cz.uhk.fim.workshop.service;

import cz.uhk.fim.workshop.exeption.EventNotFoundException;
import cz.uhk.fim.workshop.model.Event;
import cz.uhk.fim.workshop.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository repo;

    public List<Event> getEvents(){
        return (List<Event>) repo.findAll();
    }

    public Event addEvent(Event event){
        return repo.save(event);
    }

    public void removeEvent(Event e){
        repo.delete(e);
    }

    public void removeEvent(int id) throws EventNotFoundException {
        Event e = getEvent(id);
        repo.delete(e);
    }

    public Event updateEvent(Event e){
        return repo.save(e);
    }

    public Event getEvent(int id) throws EventNotFoundException{
        Optional<Event> eventOptional = repo.findById(id);
        if(eventOptional.isPresent())
            return eventOptional.get();
        else{
            throw new EventNotFoundException("Event with given ID was not found!");
        }
    }


    public List<Event> findEvent(String name){
        return repo.findEvent(name);
    }
}
