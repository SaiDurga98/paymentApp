package com.techie.springBootPaymentApp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping
    public String home() {
        return "index";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }



}
