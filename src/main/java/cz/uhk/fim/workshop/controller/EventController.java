package cz.uhk.fim.workshop.controller;

import cz.uhk.fim.workshop.dtoIn.EventDtoIn;
import cz.uhk.fim.workshop.exeption.EventNotFoundException;
import cz.uhk.fim.workshop.model.CustomUserDetails;
import cz.uhk.fim.workshop.model.Event;
import cz.uhk.fim.workshop.service.CustomUserDetailsService;
import cz.uhk.fim.workshop.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class EventController {
    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private EventService service;

    @Autowired
    private Validator validator;

    @GetMapping("/events")
    public String showEvents(Model model){
        List<Event> events = service.getEvents();
        model.addAttribute("eventList", events);
        return "events";
    }

    @GetMapping("/events/add")
    public String showNewForm(Model model){
        if(model.getAttribute("event") == null) {
            model.addAttribute("event", new EventDtoIn());
        }

        return "event_form";
    }

    @PostMapping("/events/save")
    public String saveEvent(EventDtoIn e, RedirectAttributes ra, HttpServletRequest r, Authentication authentication){
        Set<ConstraintViolation<EventDtoIn>> violations = validator.validate(e);
       if(!violations.isEmpty()){
            ra.addFlashAttribute("event", e);
            ra.addFlashAttribute("error", violations.stream().map(ConstraintViolation::getMessageTemplate).collect(Collectors.joining(", ")).toLowerCase());
            return "redirect:" + r.getHeader("referer");
        }else{
            UserDetails userDetails =  (UserDetails) authentication.getPrincipal();
            CustomUserDetails custom = (CustomUserDetails) userService.loadUserByUsername(userDetails.getUsername());

            Event event = Event.parseDtoIn(e);
            event.setUser(custom.getUser());

            service.addEvent(event);

            ra.addFlashAttribute("message", "Event has been saved successfully");
            return "redirect:/events";
        }
    }

    @GetMapping("/events/edit/{id}")
    //todo authentication a porovnat s majitelem eventu
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            if(model.getAttribute("event") == null) {
                Event e = service.getEvent(id);
                model.addAttribute("event", e);
            }
            model.addAttribute("pageTitle", "Edit Event (ID: " + id + ")");
            return "event_form";
        } catch (EventNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            ex.printStackTrace();
            return "redirect:/events";
        }

    }

    @GetMapping("/events/delete/{id}")
    //authentication a porovnat s majitelem eventu
    public String deleteEvent(@PathVariable("id") Integer id, RedirectAttributes ra){
        try {
            service.removeEvent(id);
            ra.addFlashAttribute("message", "Event was successfully removed");
        } catch (EventNotFoundException e) {
            ra.addFlashAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/events";
    }


}
