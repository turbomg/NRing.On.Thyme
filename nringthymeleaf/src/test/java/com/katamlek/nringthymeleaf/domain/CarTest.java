package com.katamlek.nringthymeleaf.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {

    Car car;

    @Before
    public void setUp(){
        car = new Car();
    }

    @Test
    public void getId() throws Exception {
        Long idValue = 4L;

        car.setId(idValue);

        assertEquals(idValue, car.getId());
    }

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
