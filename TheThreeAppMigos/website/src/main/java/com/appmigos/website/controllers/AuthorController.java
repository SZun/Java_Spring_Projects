/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.controllers;

import com.appmigos.website.dtos.Page;
import com.appmigos.website.dtos.Post;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.InvalidNameException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.service.PostService;
import com.appmigos.website.service.StaticPageService;
import com.appmigos.website.service.TagService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author samg.zun
 */
@Controller
public class AuthorController {

    @Autowired
    PostService service;

    @Autowired
    StaticPageService pageService;
    
    @Autowired
    TagService tagService;

    @GetMapping("/author/{name}")
    public String displayAuthorPage(@PathVariable String name, Model pageModel) throws InvalidNameException {
        List<Post> authorPosts = new ArrayList<>();
        List<Page> allPages = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        try {
            authorPosts = service.getPostsByName(name);
        } catch (NoItemsException ex) {
        }
        try {
            allPages = pageService.getAllPages();
        } catch (NoItemsException ex) {
        }
        try {
            allTags = tagService.getAllTags();
        } catch (NoItemsException ex) {
        }
        pageModel.addAttribute("pages", allPages);
        pageModel.addAttribute("blogs", authorPosts);
        pageModel.addAttribute("tags", allTags);
        return "author";
    }

}
