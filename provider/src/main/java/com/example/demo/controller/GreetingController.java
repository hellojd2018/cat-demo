package com.example.demo.controller;

import com.example.demo.mapper.CityMapper;
import com.example.demo.model.City;
import com.example.demo.service.UserService;
import hello.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private CityMapper cityMapper;

    @Autowired
    UserService userService;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        userService.delete(null);
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }


    @RequestMapping("/city")
    public City cityInfo(@RequestParam(value = "id") Integer id) {
        userService.save(null);
        userService.getAllObjects();
        return cityMapper.selectByPrimaryKey(id);
    }
}