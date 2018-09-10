package com.main.warehouse.controller;

import com.main.warehouse.Form.Form;
import com.main.warehouse.dao.CategoryDao;
import com.main.warehouse.dao.CountryDao;
import com.main.warehouse.dao.ItemDao;
import com.main.warehouse.entity.Category;
import com.main.warehouse.entity.Country;
import com.main.warehouse.entity.Item;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.FileReader;


@Controller
@RequestMapping("panel")
public class MainController {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CountryDao countryDao;

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
        ModelAndView modelAndView = new ModelAndView("item/add-goods");
        modelAndView.addObject("categoryList", categoryDao.findAll());
        modelAndView.addObject("countryList", countryDao.findAll());
        modelAndView.addObject("form", new Form());
        return modelAndView;
    }

    @PostMapping("/add-goods/statement")
    public ModelAndView addGoodsStatement(String name, long quantity, double price, @ModelAttribute("form") Form form, String description) {
        String message = "New item Successfully Added!";
        try {
            if (form.getCategory().getCategoryId() == -1)
                form.setCategory(null);
            if (form.getCountry().getCountryId() == -1)
                form.setCountry(null);

            itemDao.save(new Item(name, quantity, price, form.getCategory(), form.getCountry(), description));
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

    @GetMapping("/load-countries")
    public ModelAndView loadCountries(){
        return new ModelAndView("country/load-countries");
    }

    @PostMapping("load-countries/statement")
    public ModelAndView loadCountriesStatement(){
        String message = "Countries Loaded Successfully!";
        try {
            JSONArray array = new JSONArray(new JSONTokener(new FileReader("./src/main/resources/additional/countries.json")));
            for(int i=0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                String name = jsonObject.getString("countryName");
                String code = jsonObject.getString("countryCode");
                if (countryDao.findByCountryCode(code) == null && countryDao.findAllByCountryName(name) == null)
                    countryDao.save(new Country(name, code));
            }
        }catch (JSONException | FileNotFoundException e)
        {
            message = "An Error Occurred During Loading Country List!";
            e.printStackTrace();
        }

        ModelAndView modelAndView = new ModelAndView("statement");
        modelAndView.addObject("message",message);
        return modelAndView;
    }
}
