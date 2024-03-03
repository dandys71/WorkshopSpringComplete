package cz.uhk.fim.workshop.controller;

import cz.uhk.fim.workshop.dtoIn.EventDtoIn;
import cz.uhk.fim.workshop.dtoIn.UpdateEventDtoIn;
import cz.uhk.fim.workshop.dtoOut.EventDtoOut;
import cz.uhk.fim.workshop.exeption.EventNotFoundException;
import cz.uhk.fim.workshop.model.CustomUserDetails;
import cz.uhk.fim.workshop.model.Event;
import cz.uhk.fim.workshop.service.CustomUserDetailsService;
import cz.uhk.fim.workshop.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventApiController {

    @Autowired
    private EventService service;

    @Autowired
    private CustomUserDetailsService userService;

    @GetMapping("/getEventList")
    public List<Event> getEvents(){
        return service.getEvents();
    }

    @GetMapping("/getEvent/{id}")
    public EventDtoOut getEventById(@PathVariable("id") int id) throws EventNotFoundException {

        return  service.getEvent(id).toDtoOut();
    }

    @PostMapping("/addEvent")
    //todo získat authentication
    public EventDtoOut addEvent(@Valid @RequestBody EventDtoIn e){
        Event newEvent = service.addEvent(Event.parseDtoIn(e));
        return newEvent.toDtoOut();
    }

    @PostMapping("/updateEvent")
    public EventDtoOut updateEvent(@Valid @RequestBody UpdateEventDtoIn e, Authentication authentication) {
        UserDetails userDetails =  (UserDetails) authentication.getPrincipal();
        CustomUserDetails custom = (CustomUserDetails) userService.loadUserByUsername(userDetails.getUsername());

        Event event = Event.parseDtoIn(e);
        event.setUser(custom.getUser());

        return service.updateEvent(event).toDtoOut();
    }

    @GetMapping("/deleteEvent/{id}")
    //todo získat authentication a porovnat s majitelem eventu
    public ResponseEntity<String> deleteEvent(@PathVariable("id") int id) throws EventNotFoundException {
        service.removeEvent(id);
        return ResponseEntity.ok("Event was deleted");
    }
}
