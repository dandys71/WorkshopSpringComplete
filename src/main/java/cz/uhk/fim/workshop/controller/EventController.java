package cz.uhk.fim.workshop.controller;

import cz.uhk.fim.workshop.exeption.EventNotFoundException;
import cz.uhk.fim.workshop.model.Event;
import cz.uhk.fim.workshop.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
public class EventController {
    @Autowired
    private EventService service;

    @GetMapping("/events")
    public String showEvents(Model model){
        List<Event> events = service.getEvents();
        model.addAttribute("eventList", events);
        return "events";
    }

    @GetMapping("/events/add")
    public String showNewForm(Model model){
        model.addAttribute("event", new Event());
        return "event_form";
    }

    @PostMapping("/events/save")
    public String saveEvent(Event e, RedirectAttributes ra){
        service.addEvent(e);
        ra.addFlashAttribute("message", "Event has been created successfully");
        return "redirect:/events";
    }

    @GetMapping("/events/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            Event e = service.getEvent(id);
            model.addAttribute("event", e);
            model.addAttribute("pageTitle", "Edit Event (ID: " + id + ")");
            return "event_form";
        } catch (EventNotFoundException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            ex.printStackTrace();
            return "redirect:/events";
        }

    }

    @GetMapping("/events/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra){
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
