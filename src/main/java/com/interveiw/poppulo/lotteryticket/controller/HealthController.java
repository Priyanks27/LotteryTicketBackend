package com.interveiw.poppulo.lotteryticket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String healthCheck(){
        return "Welcome to Lottery ticket generation system";
    }
}
