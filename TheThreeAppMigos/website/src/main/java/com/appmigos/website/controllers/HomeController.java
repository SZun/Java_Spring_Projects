/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.controllers;

import com.appmigos.website.dtos.Page;
import com.appmigos.website.dtos.Post;
import com.appmigos.website.dtos.Tag;
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

/**
 *
 * @author samg.zun
 */
@Controller
public class HomeController {
    
    @Autowired
    PostService service;
    
    @Autowired
    StaticPageService pageService;
    
    @Autowired
    TagService tagService;
    
    @GetMapping({"/", "/home"})
    public String displayHome(Model pageModel){
        List<Post> allBlogs = new ArrayList<>();
        List<Page> allPages = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        try {
            allBlogs = service.getAllPosts(true);
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
        
        int lastFiveSize = 5;
        if(allBlogs.size() < lastFiveSize){
            lastFiveSize = allBlogs.size();
        }
        
        Post[] lastFive = new Post[lastFiveSize];
        
        for(int i = 0; i < 5; i++){
            if(i < allBlogs.size()){
                lastFive[i] = allBlogs.get(allBlogs.size() - 1 - i);
            } else {
                break;
            }
        }
        
        
        pageModel.addAttribute("blogs", lastFive);
        pageModel.addAttribute("pages", allPages);
        pageModel.addAttribute("tags", allTags);
        return "home";
    }
    
}
