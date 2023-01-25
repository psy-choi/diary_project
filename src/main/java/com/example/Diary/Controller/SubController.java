package com.example.Diary.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SubController {

    @GetMapping("/")
    public String Calendar() {

        return "index";
    }
}
