package com.katamlek.nringthymeleaf.domain;

import com.katamlek.nringthymeleaf.repositories.CarRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Entity;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarTest {

    @Autowired
    private CarRepository carRepository;

//    @Rule
//    public Car car;

//    @Before
//    public void setUp() {
//        Car car = new Car();
//
//    }

    // Read test methods
    @Test
    // No items if repository empty
    public void findNoItems() {
        Car car = new Car();
        car.setModel("Ford");

        Iterable<Car> cars = carRepository.findAll();

        assertThat(cars).hasSize(0);
    }

    @Test
    // One item found if one saved, no criteria
    public void testFindAll() {
        Car car = new Car();

        car.setModel("Ford");

        carRepository.save(car);

        Iterable<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(1);
        assertThat(cars).contains(car);
    }

    @Test
    public void testFindById() {
        Car car = new Car();
        car.setModel("Fiat");

        carRepository.save(car);

        Iterable<Car> cars = carRepository.findAll();
        assertThat(cars).contains(car);

        Car foundById = carRepository.findById(car.getId()).get();
        assertThat(foundById.equals(car));

    }

    // Create test methods
    @Test
    public void save() {

        Car persisted = new Car();
        persisted.setModel("Citroen");

        carRepository.save(persisted);

        Iterable<Car> cars = carRepository.findAll();

        assertThat(cars).contains(persisted);

        Car found = carRepository.findById(persisted.getId()).get();

        assertThat(found.getModel().equalsIgnoreCase(" citroen"));

    }
//
//    @Test
//    // Read
//
//    @Test
//    // Update
//
//
//    @Test
//    // Delete
//
//
//    public void getId() throws Exception {
//        Long idValue = 4L;
//
//        car.setId(idValue);
//
//        assertEquals(idValue, car.getId());
//    }

    //TODO other tests of the domain objects

//
//    @Test
//    public void getDescription() throws Exception {
//    }
//
//    @Test
//    public void getRecipes() throws Exception {
//    }
}
