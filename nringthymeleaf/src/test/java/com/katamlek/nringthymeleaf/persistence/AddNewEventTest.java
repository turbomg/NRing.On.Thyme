package com.katamlek.nringthymeleaf.persistence;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.repositories.BookingRepository;
import com.katamlek.nringthymeleaf.repositories.EventRepository;
import com.katamlek.nringthymeleaf.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddNewEventTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveEventDataOnly() {
        Event event = new Event();
        event.setEventName("New Event");
        event.setEventType(EventType.TRACKDAY);
        event.setEventNoiseLimit("100");
        event.setEventCarTypes("Blue Cars");

        eventRepository.save(event);

        assertThat((eventRepository.save(event)!=null));
    }

    @Test
    public void saveEventWithResponsibleUser() {
        Event event = new Event();
        event.setEventName("New Event");
        event.setEventType(EventType.TRACKDAY);
        event.setEventNoiseLimit("100");
        event.setEventCarTypes("Blue Cars");

        User user = new User();
        user.setName("User Name");
        event.setEventResponsibleUser(user);

        assertThat((eventRepository.save(event)!=null));

        Iterable<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
        assertThat(users).contains(user);


    }
}
