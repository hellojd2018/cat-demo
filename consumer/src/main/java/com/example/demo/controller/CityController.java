package com.example.demo.controller;

import com.example.demo.service.ApiService;
import com.example.demo.service.CityClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CityController {
    private static Logger LOGGER = LoggerFactory.getLogger(CityController.class);
    @Autowired
    ApiService apiService;
    @Autowired
    CityClient cityClient;
    @RequestMapping("/consumer-city")
    public String cityInfo(Integer cityId) throws IOException {
        String resp = apiService.doGet("http://www.baidu.com");
        LOGGER.info(resp);
        return cityClient.cityInfo(cityId);
    }
}
