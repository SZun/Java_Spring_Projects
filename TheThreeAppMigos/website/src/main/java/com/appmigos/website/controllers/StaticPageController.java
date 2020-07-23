/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.controllers;

import com.appmigos.website.dtos.Page;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.service.StaticPageService;
import com.appmigos.website.service.TagService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author samg.zun
 */
@Controller
public class StaticPageController {

    @Autowired
    StaticPageService service;

    @Autowired
    TagService tagService;

    @GetMapping("static-pages")
    public String displayStaticPages(Model pageModel) {
        List<Page> allPages = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        try {
            allPages = service.getAllPages();
        } catch (NoItemsException ex) {
        }
        try {
            allTags = tagService.getAllTags();
        } catch (NoItemsException ex) {
        }
        pageModel.addAttribute("staticpages", allPages);
        pageModel.addAttribute("tags", allTags);
        return "staticPages";
    }

    @GetMapping("static-page-add")
    public String displayAddStaticPage(Model pageModel) {
        List<Page> allPages = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        try {
            allPages = service.getAllPages();
        } catch (NoItemsException ex) {
        }
        try {
            allTags = tagService.getAllTags();
        } catch (NoItemsException ex) {
        }
        Page toAdd = new Page();
        pageModel.addAttribute("page", toAdd);
        pageModel.addAttribute("staticpage", toAdd);
        pageModel.addAttribute("staticpages", allPages);
        pageModel.addAttribute("tags", allTags);
        return "addStaticPage";
    }

    @PostMapping("addstaticpage")
    public String addStaticPage(@Valid Page toAdd, BindingResult res, Model pageModel) throws InvalidEntityException, InvalidIdException {
        List<Page> allPages = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        try {
            allPages = service.getAllPages();
        } catch (NoItemsException ex) {
        }
        try {
            allTags = tagService.getAllTags();
        } catch (NoItemsException ex) {
        }
        if (res.hasErrors()) {
            pageModel.addAttribute("staticpage", toAdd);
            pageModel.addAttribute("staticpages", allPages);
            pageModel.addAttribute("tags", allTags);
            return "addStaticPage";
        }

        service.createPage(toAdd);
        return "redirect:/static-pages";
    }

    @GetMapping("static-page/{id}")
    public String displayStaticPageDetail(@PathVariable Integer id, Model pageModel) throws InvalidIdException {
        List<Page> allPages = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        try {
            allPages = service.getAllPages();
        } catch (NoItemsException ex) {
        }
        try {
            allTags = tagService.getAllTags();
        } catch (NoItemsException ex) {
        }
        Page toDisplay = service.getPageById(id);
        pageModel.addAttribute("staticpage", toDisplay);
        pageModel.addAttribute("staticpages", allPages);
        pageModel.addAttribute("tags", allTags);
        return "staticPageDetail";
    }

    @GetMapping("static-page-edit")
    public String displayEditPage(Integer id, Model pageModel) throws InvalidIdException {
        List<Page> allPages = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        try {
            allPages = service.getAllPages();
        } catch (NoItemsException ex) {
        }
        try {
            allTags = tagService.getAllTags();
        } catch (NoItemsException ex) {
        }
        Page toEdit = service.getPageById(id);
        pageModel.addAttribute("staticpage", toEdit);
        pageModel.addAttribute("page", new Page());
        pageModel.addAttribute("staticpages", allPages);
        pageModel.addAttribute("tags", allTags);
        return "editStaticPage";
    }

    @PostMapping("editstaticpage")
    public String editStaticPage(@Valid Page toEdit, BindingResult res, Model pageModel) throws InvalidEntityException, InvalidIdException {
        if (res.hasErrors()) {
            List<Page> allPages = new ArrayList<>();
            List<Tag> allTags = new ArrayList<>();
            try {
                allPages = service.getAllPages();
            } catch (NoItemsException ex) {
            }
            try {
                allTags = tagService.getAllTags();
            } catch (NoItemsException ex) {
            }
            pageModel.addAttribute("staticpage", toEdit);
            pageModel.addAttribute("staticpages", allPages);
            pageModel.addAttribute("tags", allTags);
            return "editStaticPage";
        }

        service.editPage(toEdit);
        return "redirect:/static-pages";
    }

    @GetMapping("deletestaticpage")
    public String deleteStaticPage(Integer id) throws InvalidIdException {
        service.deletePage(id);
        return "redirect:/static-pages";
    }

}
