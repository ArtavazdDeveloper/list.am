package com.example.list_am.controller;

import com.example.list_am.entity.Category;
import com.example.list_am.entity.Comment;
import com.example.list_am.entity.Item;
import com.example.list_am.repository.CategoryRepository;
import com.example.list_am.repository.CommentRepository;
import com.example.list_am.repository.HashtagRepository;
import com.example.list_am.repository.ItemRepository;
import com.example.list_am.security.CurrentUser;
import com.example.list_am.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@RequestMapping("/items")
@RequiredArgsConstructor
@Controller
public class ItemController {


    private final ItemService itemService;
    private final CommentRepository commentRepository;
    private final CategoryRepository categoryRepository;
    private final HashtagRepository hashtagRepository;


    @GetMapping
    public String itemsPage(ModelMap modelMap,
                            @AuthenticationPrincipal CurrentUser currentUser,
                            Locale locale) {
        modelMap.addAttribute("items", itemService.findItemsByUser(currentUser.getUser()));
        modelMap.addAttribute("categories", categoryRepository.findAll());
        return "items";
    }

    @GetMapping("/{id}")
    public String singleItemPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Item> byId = itemService.findById(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            List<Comment> comments = commentRepository.findAllByItem_Id(item.getId());
            modelMap.addAttribute("item", item);
            modelMap.addAttribute("comments", comments);
            return "singleItem";
        } else {
            return "redirect:/items";
        }

    }

    @GetMapping("/add")
    public String itemsAddPage(ModelMap modelMap) {
        List<Category> all = categoryRepository.findAll();
        modelMap.addAttribute("categories", all);
        modelMap.addAttribute("hashtags", hashtagRepository.findAll());
        return "addItem";
    }

    @PostMapping("/add")
    public String itemsAdd(@ModelAttribute Item item,
                           @RequestParam("image") MultipartFile multipartFile,
                           @AuthenticationPrincipal CurrentUser currentUser) throws IOException {
        itemService.addItem(currentUser.getUser(), multipartFile, item);
        return "redirect:/items";
    }

    @GetMapping("/remove")
    public String removeCategory(@RequestParam("id") int id) {
        itemService.deleteById(id);
        return "redirect:/items";
    }

    @PostMapping("/comment/add")
    public String addComment(@ModelAttribute Comment comment,
                             @AuthenticationPrincipal CurrentUser currentUser) {
        comment.setCommentDate(new Date());
        comment.setUser(currentUser.getUser());
        commentRepository.save(comment);
        return "redirect:/items/" + comment.getItem().getId();
    }
}