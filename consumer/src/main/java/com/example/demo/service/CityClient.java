package com.example.demo.service;

import com.dianping.cat.context.CatFeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "provider",url = "http://localhost:9090", configuration = CatFeignConfiguration.class)
public interface CityClient {
    @GetMapping("/city")
    String cityInfo(@RequestParam(value = "id") Integer id);
}
