package com.main.warehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("panel")
public class MainController {

    @GetMapping("")
    public ModelAndView panel() {
        return new ModelAndView("panel");
    }

}
