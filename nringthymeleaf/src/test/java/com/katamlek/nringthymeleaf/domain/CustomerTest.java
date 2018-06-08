package com.katamlek.nringthymeleaf.domain;

import com.katamlek.nringthymeleaf.repositories.CarRepository;
import com.katamlek.nringthymeleaf.repositories.CustomerRepository;
import com.katamlek.nringthymeleaf.services.CustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerTest {

    Car car;

    @Before
    public void setUp() {
        car = new Car();
    }

    @Test
    public void getId() throws Exception {
        Long idValue = 4L;

        car.setId(idValue);

        assertThat(idValue).isEqualTo(car.getId());
    }

    @Test
    public void getCustomers() throws Exception {
    }
//
//    @Test
//    public void getRecipes() throws Exception {
//    }

//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private CustomerServiceImpl customerServiceImpl;
//
//    // Read test methods
//    @Test
//    // No items if repository empty
//    public void findNoItems() {
//        Customer customer = new Customer();
//        customer.setCustomerFirstName("Ewa");
//
//        Iterable<Customer> customers = customerRepository.findAll();
//
//        assertThat(customers).hasSize(0);
//    }
//
//    @Test
//    // One item found if one saved, no criteria
//    public void testFindAll() {
//        Customer customer = new Customer();
//        customer.setCustomerFirstName("Alicja");
//
//        customerRepository.save(customer);
//
//        Iterable<Customer> customers = customerRepository.findAll();
//
//        assertThat(customers).hasSize(1);
//        assertThat(customers).contains(customer);
//    }
//
//    @Test
//    public void testFindById() {
//        Customer customer = new Customer();
//        customer.setCustomerFirstName("Basia");
//
//        customerRepository.save(customer);
//
//        Iterable<Customer> customers = customerRepository.findAll();
//        assertThat(customers).contains(customer);
//
//        Customer foundById = customerRepository.findById(customer.getId()).get();
//        assertThat(foundById.equals(customer));
//    }
//
//    // Create test methods
//    @Test
//    public void save() {
//        Customer persisted = new Customer();
//        persisted.setCustomerFirstName("Lena");
//
//        customerRepository.save(persisted);
//
//        Iterable<Customer> customers = customerRepository.findAll();
//
//        assertThat(customers).contains(persisted);
//
//        Customer found = customerRepository.findById(persisted.getId()).get();
//
//        assertThat(found.getCustomerFirstName().equalsIgnoreCase("lena"));
//    }
//
//    // Update test methods
//    @Test
//    public void update() {
//        Customer customer = new Customer();
//        customer.setCustomerFirstName("Kasia");
//
//        customerRepository.save(customer);
//
//        Iterable<Customer> customers = customerRepository.findAll();
//
//        assertThat(customers).contains(customer);
//
//        customerRepository.findById(customer.getId());
//        customer.setCustomerFirstName("Agata");
//        customerRepository.save(customer);
//
//        Customer found = customerRepository.findById(customer.getId()).get();
//
//        assertThat(found.getCustomerFirstName().equalsIgnoreCase("agata"));
//    }
//
//    // Delete test methods
//    @Test
//    public void delete() {
//        Customer customer = new Customer();
//        customer.setCustomerFirstName("Ola");
//
//        customerRepository.save(customer);
//
//        Iterable<Customer> customers = customerRepository.findAll();
//
//        assertThat(customers).contains(customer);
//
//        customerRepository.delete(customer);
//
//        customers = customerRepository.findAll();
//
//        assertThat(customers).doesNotContain(customer);
//    }
}
