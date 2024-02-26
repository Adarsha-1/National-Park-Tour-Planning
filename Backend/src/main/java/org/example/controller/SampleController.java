package org.example.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class SampleController {

    @GetMapping(value = "/login/oauth2/sample")
    @CrossOrigin(origins = "http://localhost:3000")
    public String welcome() {
        System.out.println("entered welcome oauth2");
        return "Welcome to Google !!";
    }
}
