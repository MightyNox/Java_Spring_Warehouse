package com.main.warehouse.controller;

import com.main.warehouse.dao.CategoryDao;
import com.main.warehouse.dao.ItemDao;
import com.main.warehouse.entity.Category;
import com.main.warehouse.entity.Item;
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

    @Autowired
    private CategoryDao categoryDao;

    @GetMapping("")
    public ModelAndView panel() {
        return new ModelAndView("panel");
    }

    @GetMapping("/show-goods")
    public ModelAndView showGoods() {
        return new ModelAndView("item/show-goods");
    }

    @GetMapping("/add-goods")
    public ModelAndView addGoods() {
        return new ModelAndView("item/add-goods");
    }

    @PostMapping("/add-goods/statement")
    public ModelAndView addGoodsStatement(String name, long quantity, double price, String category, String producer, String description) {
        String message = "New item Successfully Added!";
        try {
            itemDao.save(new Item(name, quantity, price, null, null, description));
        }
        catch (Exception e)
        {
            message = "An error occurred during adding new item!";
        }

        ModelAndView modelAndView = new ModelAndView("statement");
        modelAndView.addObject("message",message);
        return modelAndView;
    }

    @GetMapping("/edit-goods")
    public ModelAndView editGoods(){
        return new ModelAndView("item/edit-goods");
    }

    @GetMapping("/delete-goods")
    public ModelAndView deleteGoods() {
        return new ModelAndView("item/delete-goods");
    }

    @GetMapping("/add-category")
    public ModelAndView addCategory(){
        return new ModelAndView("category/add-category");
    }

    @PostMapping("/add-category/statement")
    public ModelAndView addCategoryStatement(String name){
        String message = "New category successfully added!";

        if(categoryDao.findByCategoryName(name)!= null)
            message="This category exists!";
        else {

            try {
                categoryDao.save(new Category(name));
            } catch (Exception e) {
                message = "An error occurred during adding new category!";
            }
        }

        ModelAndView modelAndView = new ModelAndView("statement");
        modelAndView.addObject("message",message);
        return modelAndView;
    }

    @GetMapping("/delete-category")
    public ModelAndView deleteCategory(){
        return new ModelAndView("category/delete-category");
    }
}
