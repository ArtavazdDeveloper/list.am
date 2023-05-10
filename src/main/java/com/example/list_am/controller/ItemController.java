package com.example.list_am.controller;

import com.example.list_am.entity.Category;
import com.example.list_am.entity.Item;
import com.example.list_am.repository.CategoryRepository;
import com.example.list_am.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping("/items")
    public String itemsPage(ModelMap modelMap){
        List<Item> all = itemRepository.findAll();
        modelMap.addAttribute("items",all);
        return "items";
    }
    @GetMapping("/items/{id}")
    public String singleItemPage(@PathVariable("id") int id,
                                 ModelMap modelMap){
        Optional<Item> byId = itemRepository.findById(id);
        if (byId.isPresent()){
            Item item = byId.get();
            modelMap.addAttribute("item",item);
            return "singleItem";
        }else {
            return "redirect:/items";
        }
    }
    @GetMapping("/items/add")
    public String itemsAddPage(ModelMap modelMap){
        List<Category> all = categoryRepository.findAll();
        modelMap.addAttribute("categories", all);
        return "addItem";
    }
    @PostMapping("/items/add")
    public String addItem(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/items/remove")
    public String removeCategory(@RequestParam("id") int id){
        itemRepository.deleteById(id);
        return "redirect:/items";
    }
}
