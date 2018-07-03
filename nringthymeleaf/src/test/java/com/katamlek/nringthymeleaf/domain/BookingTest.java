package com.katamlek.nringthymeleaf.domain;

import com.katamlek.nringthymeleaf.repositories.BookingRepository;
import com.katamlek.nringthymeleaf.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookingTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    // Read test methods
    @Test
    // No items if repository empty
    public void findNoItems() {
        Booking booking = new Booking();
        booking.setPaymentStatus(PaymentStatus.NOT);

        Iterable<Booking> bookings = bookingRepository.findAll();

        assertThat(bookings).hasSize(0);
    }

    @Test
    // One item found if one saved, no criteria
    public void testFindAll() {
        Booking booking = new Booking();
        User user = new User();
        user.setEmail("kasia@mlek.pl");
        userRepository.save(user);

        booking.setCreatedBy(user);
        booking.setCreateDate(new Date());
        booking.setSignatureStatus(SignatureStatus.SIGNED);
        booking.setCustomers(new ArrayList<Customer>());

        booking.setPaymentStatus(PaymentStatus.IN_FULL);

        bookingRepository.save(booking);

        Iterable<Booking> bookings = bookingRepository.findAll();
        assertThat(bookings).hasSize(1);
        assertThat(bookings).contains(booking);
    }

    @Test
    public void testFindById() {
        Booking booking = new Booking();

        User user = new User();
        user.setEmail("kasia@mlek.pl");
        userRepository.save(user);

        booking.setCreatedBy(user);
        booking.setCreateDate(new Date());
        booking.setSignatureStatus(SignatureStatus.SIGNED);
        booking.setCustomers(new ArrayList<Customer>());

        booking.setPaymentStatus(PaymentStatus.PARTIALLY);

        bookingRepository.save(booking);

        Iterable<Booking> bookings = bookingRepository.findAll();
        assertThat(bookings).contains(booking);

        Booking foundById = bookingRepository.findById(booking.getId()).get();
        assertThat(foundById.equals(booking));
    }

    // Create test methods
    @Test
    public void save() {
        Booking persisted = new Booking();

        User user = new User();
        user.setEmail("kasia@mlek.pl");
        userRepository.save(user);

        persisted.setCreatedBy(user);
        persisted.setCreateDate(new Date());
        persisted.setSignatureStatus(SignatureStatus.SIGNED);
        persisted.setCustomers(new ArrayList<Customer>());

        persisted.setPaymentStatus(PaymentStatus.NOT);

        bookingRepository.save(persisted);

        Iterable<Booking> bookings = bookingRepository.findAll();

        assertThat(bookings).contains(persisted);

        Booking found = bookingRepository.findById(persisted.getId()).get();

        assertThat(found.getPaymentStatus().equals(PaymentStatus.NOT));
    }

    // Update test methods
    @Test
    public void update() {
        Booking booking = new Booking();

        User user = new User();
        user.setEmail("kasia@mlek.pl");
        userRepository.save(user);

        booking.setCreatedBy(user);
        booking.setCreateDate(new Date());
        booking.setSignatureStatus(SignatureStatus.SIGNED);
        booking.setCustomers(new ArrayList<Customer>());

        booking.setPaymentStatus(PaymentStatus.IN_FULL);

        bookingRepository.save(booking);

        Iterable<Booking> bookings = bookingRepository.findAll();

        assertThat(bookings).contains(booking);

        bookingRepository.findById(booking.getId());
        booking.setPaymentStatus(PaymentStatus.NOT);
        bookingRepository.save(booking);

        Booking found = bookingRepository.findById(booking.getId()).get();

        assertThat(found.getPaymentStatus().equals(PaymentStatus.NOT));
    }

    // Delete test methods
    @Test
    public void delete() {
        Booking booking = new Booking();

        User user = new User();
        user.setEmail("kasia@mlek.pl");
        userRepository.save(user);

        booking.setCreatedBy(user);
        booking.setCreateDate(new Date());
        booking.setSignatureStatus(SignatureStatus.SIGNED);
        booking.setCustomers(new ArrayList<Customer>());

        booking.setPaymentStatus(PaymentStatus.PARTIALLY);

        bookingRepository.save(booking);

        Iterable<Booking> bookings = bookingRepository.findAll();

        assertThat(bookings).contains(booking);

        bookingRepository.delete(booking);

        bookings = bookingRepository.findAll();

        assertThat(bookings).doesNotContain(booking);
    }
}
