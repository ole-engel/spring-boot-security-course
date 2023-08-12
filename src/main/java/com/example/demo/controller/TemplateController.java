package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/")
public class TemplateController {

    @RequestMapping("login")
    public String getLogin() {
        return "login";
    }

    @RequestMapping("courses")
    public String getCourses() {
        return "courses";
    }
}
