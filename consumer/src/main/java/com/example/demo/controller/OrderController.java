package com.example.demo.controller;

import com.dianping.cat.Cat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {

    @RequestMapping("/order")
    public String order(){
        //记录一个业务指标,主要衡量单位时间内的次数总和
        Cat.logMetricForCount("OrderCount",1);
        return UUID.randomUUID().toString();
    }

    @RequestMapping("/timer")
    public String timer(){
        //记录一个timer类业务指标，主要衡量单位时间内平均值
        Cat.logMetricForDuration("KeyForTimer",5);
        return UUID.randomUUID().toString();
    }
}
