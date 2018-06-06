package com.katamlek.nringthymeleaf.domain;

import com.katamlek.nringthymeleaf.repositories.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventTest {

    @Autowired
    private CarRepository carRepository;

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

    // Update test methods
    @Test
    public void update() {
        Car car = new Car();
        car.setModel("BMW");

        carRepository.save(car);

        Iterable<Car> cars = carRepository.findAll();

        assertThat(cars).contains(car);

        carRepository.findById(car.getId());
        car.setModel("Kia");
        carRepository.save(car);

        Car found = carRepository.findById(car.getId()).get();

        assertThat(found.getModel().equalsIgnoreCase(" kia"));
    }

    // Delete test methods
    @Test
    public void delete() {
        Car car = new Car();
        car.setModel("Ferrari");

        carRepository.save(car);

        Iterable<Car> cars = carRepository.findAll();

        assertThat(cars).contains(car);

        carRepository.delete(car);

        cars = carRepository.findAll();

        assertThat(cars).doesNotContain(car);
    }
}
