//package com.katamlek.nringthymeleaf.bootstrap;
//
//import com.katamlek.nringthymeleaf.domain.Event;
//import com.katamlek.nringthymeleaf.domain.LocationDefinition;
//import com.katamlek.nringthymeleaf.repositories.EventRepository;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//
//@Component
//public class EventLoader implements ApplicationListener<ContextRefreshedEvent> {
//
//    private EventRepository eventRepository;
//
//    private Logger log = LogManager.getLogger(EventLoader.class);
//
//    public EventLoader(EventRepository eventRepository) {
//        this.eventRepository = eventRepository;
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        initData();
//    }
//
//    private void initData() {
////        Event nightKnight = new Event();
////
////        nightKnight.setEventName("Night Knight");
////        nightKnight.setEventLocation(new LocationDefinition());
////
////        eventRepository.save(nightKnight);
////
////        log.info("Saved Night Knight - id: " + nightKnight.getId());
////
////        Event coldDawn = new Event();
////
////        coldDawn.setEventName("Cold Dawn");
////        coldDawn.setEventLocation(new LocationDefinition()  );
////
////        eventRepository.save(coldDawn);
////
////        log.info("Saved Cold Dawn - id:" + coldDawn.getId());
//    }
//}
