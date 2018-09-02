package com.main.warehouse.controller;

import com.main.warehouse.dao.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("panel")
public class MainController {

    @Autowired
    private ItemDao itemDao;

    @GetMapping("")
    public ModelAndView panel() {
        return new ModelAndView("panel");
    }

    @GetMapping("/show-goods")
    public ModelAndView showGoods() {
        return new ModelAndView("show-goods");
    }

    @GetMapping("/add-goods")
    public ModelAndView addGoods() {
        return new ModelAndView("add-goods");
    }

    @PostMapping("/statement")
    public ModelAndView addGoodsPost() {
        ModelAndView modelAndView = new ModelAndView("statement");
        modelAndView.addObject("message","Item Successfully Added!");
        return modelAndView;
    }

    @GetMapping("/delete-goods")
    public ModelAndView deleteGoods() {
        return new ModelAndView("delete-goods");
    }
}
