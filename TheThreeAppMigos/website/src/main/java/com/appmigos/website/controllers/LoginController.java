/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.controllers;

import com.appmigos.website.dtos.Page;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.service.StaticPageService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Isaia
 */
@Controller
public class LoginController {
    
    @Autowired
    StaticPageService service;
    
    @GetMapping("/login")
    public String displayLogin(Model pageModel){
        List<Page> allPages = new ArrayList<>();
        
        try {
            allPages = service.getAllPages();
        } catch(NoItemsException ex){}
        
        
        pageModel.addAttribute("pages", allPages);
        return "login";
    }
    
}
