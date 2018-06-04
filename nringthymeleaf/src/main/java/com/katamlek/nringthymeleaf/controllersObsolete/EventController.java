package com.katamlek.nringthymeleaf.controllersObsolete;

import com.katamlek.nringthymeleaf.repositories.EventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//todo no command, services, converters - well maybe I'd need a service for a dashboard or something of that kind

@Controller
public class EventController {

    private EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @RequestMapping("/events")
    public String getBooks(Model model){

        model.addAttribute("events", eventRepository.findAll());

        return "events";
    }
}

//@Controller
//public class EventController {
//
////    private EventService eventService;
////
////    @Autowired
////    public void setEventService(EventService eventService) {
////        this.eventService = eventService;
////    }
//
//    private EventRepository eventRepository;
//
//    public EventController(EventRepository eventRepository) {
//        this.eventRepository = eventRepository;
//    }
//
//    @RequestMapping("/events")
//    public String getEvents(Model model) {
//        model.addAttribute("events", eventRepository.findAll());
//        return "events";
//    }
////    @RequestMapping(value = "/events", method = RequestMethod.GET)
////    public String list(Model model){
////        model.addAttribute("events", eventService.listAllEvents());
////        System.out.println("Registered events");
////        return "events";
////    }
////
////    @RequestMapping("event/{id}")
////    public String showEvent(@PathVariable Integer id, Model model){
////        model.addAttribute("event", eventService.getEventById(id));
////        return "eventshow";
////    }
////
////    @RequestMapping("event/edit/{id}")
////    public String edit(@PathVariable Integer id, Model model){
////        model.addAttribute("event", eventService.getEventById(id));
////        return "productform";
////    }
////
////    @RequestMapping("event/new")
////    public String newEvent(Model model){
////        model.addAttribute("event", new Event());
////        return "eventform";
////    }
////
////    @RequestMapping(value = "event", method = RequestMethod.POST)
////    public String saveEvent(Event event){
////
////        eventService.saveEvent(event);
////
////        return "redirect:/event/" + event.getId();
////    }
//
//}
