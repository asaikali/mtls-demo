package com.example.mtls.greeter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greet/{name}")
    public Greeting greet(@PathVariable("name") String name)
    {
        return new Greeting( "hello " + name);
    }
}
