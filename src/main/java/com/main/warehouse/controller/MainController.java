package com.main.warehouse.controller;

import com.main.warehouse.Form.DisplayGoodsForm;
import com.main.warehouse.Form.Form;
import com.main.warehouse.dao.CategoryDao;
import com.main.warehouse.dao.CountryDao;
import com.main.warehouse.dao.ItemDao;
import com.main.warehouse.entity.Category;
import com.main.warehouse.entity.Country;
import com.main.warehouse.entity.Item;
import org.apache.catalina.LifecycleState;
import org.dom4j.rule.Mode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import java.util.ArrayList;
import java.util.List;


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
        ModelAndView modelAndView = new ModelAndView("item/show-goods");
        List<Item> itemList = itemDao.findAll();

        if (itemList.isEmpty()){
            modelAndView.addObject("displayGoodsFormList", itemList);
        }
        else {
            List<DisplayGoodsForm> displayGoodsFormList = new ArrayList<>();
            for (Item item : itemList) {
                displayGoodsFormList.add(new DisplayGoodsForm(item.getItemName(),
                                                                item.getItemQuantity(),
                                                                item.getItemPrice(),
                                                                item.getItemCategory(),
                                                                item.getItemCountry(),
                                                                item.getItemDescription()));
            }
            modelAndView.addObject("displayGoodsFormList", displayGoodsFormList);
        }
        return modelAndView;
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

        if(itemDao.findByItemName(name) != null) {
            message = "This Item Exists!";
        }
        else{
            try {
                if (form.getCategory().getCategoryId() == -1)
                    form.setCategory(null);
                if (form.getCountry().getCountryId() == -1)
                    form.setCountry(null);

                itemDao.save(new Item(name, quantity, price, form.getCategory(), form.getCountry(), description));
            } catch (Exception e) {
                message = "An error occurred during adding new item!";
            }
        }

        ModelAndView modelAndView = new ModelAndView("statement");
        modelAndView.addObject("message",message);
        return modelAndView;
    }

    @GetMapping("/edit-goods")
    public ModelAndView editGoods(){
        ModelAndView modelAndView = new ModelAndView("item/edit-goods");
        modelAndView.addObject("categoryList", categoryDao.findAll());
        modelAndView.addObject("countryList", countryDao.findAll());
        modelAndView.addObject("form", new Form());
        return modelAndView;
    }

    @PostMapping("/edit-goods/statement")
    public ModelAndView editGoodsStatement(String name, long quantity, double price, @ModelAttribute("form") Form form, String description) {
        String message = "Item Successfully Edited!";
        Item item = itemDao.findByItemName(name);

        if(item == null) {
            message = "Item \""+name+"\" Doesn't Exist!";
        }
        else{
            try {
                if (form.getCategory().getCategoryId() == -1)
                    form.setCategory(null);
                if (form.getCountry().getCountryId() == -1)
                    form.setCountry(null);

                item.setItemQuantity(quantity);
                item.setItemPrice(price);
                item.setItemCategory(form.getCategory());
                item.setItemCountry(form.getCountry());
                item.setItemDescription(description);
                itemDao.save(item);

            } catch (Exception e) {
                message = "An error occurred during editing an item!";
            }
        }

        ModelAndView modelAndView = new ModelAndView("statement");
        modelAndView.addObject("message",message);
        return modelAndView;
    }


    @GetMapping("/delete-goods")
    public ModelAndView deleteGoods() {
        return new ModelAndView("item/delete-goods");
    }

    @PostMapping("/delete-goods/statement")
    public ModelAndView deleteGoodsStatement(String name) {
        String message = "Item Successfully Deleted!";

        Item item = itemDao.findByItemName(name);
        if(item == null) {
            message = "Item \""+name+"\" Doesn't Exist!";
        }
        else{
            try {
                itemDao.delete(item);
            } catch (Exception e) {
                message = "An error occurred during deleting an item!";
            }
        }

        ModelAndView modelAndView = new ModelAndView("statement");
        modelAndView.addObject("message",message);
        return modelAndView;
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
        ModelAndView modelAndView = new ModelAndView("category/delete-category");
        modelAndView.addObject("categoryList", categoryDao.findAll());
        modelAndView.addObject("form", new Form());
        return modelAndView;
    }

    @PostMapping("/delete-category/statement")
    public ModelAndView deleteCategoryStatement(@ModelAttribute("form") Form form){
        String message = "Category Successfully Deleted!";

        if(form.getCategory().getCategoryId() == -1){
            message = "None category was selected!";
        }
        else {
            try {
                List<Item> item = itemDao.findByItemCategory(form.getCategory());
                if(item != null)
                {
                    message = "Cannot delete category because items contains it!";
                }
                else
                {
                    categoryDao.delete(form.getCategory());
                }
            } catch (Exception e) {
                message = "An error occurred during deleting category!";
            }
        }

        ModelAndView modelAndView = new ModelAndView("statement");
        modelAndView.addObject("message",message);
        return modelAndView;
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
