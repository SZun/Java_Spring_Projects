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
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author samg.zun
 */
@Controller
public class TagController {

    @Autowired
    TagService service;

    @Autowired
    StaticPageService pageService;

    @GetMapping("hashtags")
    public String displayHashtags(Model pageModel) {
        List<Tag> allHashtags = new ArrayList<>();
        List<Page> allPages = new ArrayList<>();
        Tag hashtag = new Tag();
        try {
            allHashtags = service.getAllTags();
        } catch (NoItemsException ex) {
        }
        try {
            allPages = pageService.getAllPages();
        } catch (NoItemsException ex) {
        }

        pageModel.addAttribute("tags", allHashtags);
        pageModel.addAttribute("tag", hashtag);
        pageModel.addAttribute("pages", allPages);
        return "hashtags";
    }

    @PostMapping("hashtags")
    public String addHashtag(@Valid Tag toAdd, BindingResult res, Model pageModel) throws InvalidEntityException {
        if (res.hasErrors()) {
            List<Tag> allHashtags = new ArrayList<>();
            List<Page> allPages = new ArrayList<>();
            try {
                allHashtags = service.getAllTags();
            } catch (NoItemsException ex) {
            }
            try {
                allPages = pageService.getAllPages();
            } catch (NoItemsException ex) {
            }

            pageModel.addAttribute("tags", allHashtags);
            pageModel.addAttribute("tag", toAdd);
            pageModel.addAttribute("pages", allPages);
            return "hashtags";
        }

        service.createTag(toAdd);
        return "redirect:/hashtags";
    }

    @GetMapping("hashtag-edit")
    public String displayEditHashtag(Integer id, Model pageModel) throws InvalidIdException {
        List<Page> allPages = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        try {
            allPages = pageService.getAllPages();
        } catch (NoItemsException ex) {
        }
        try {
            allTags = service.getAllTags();
        } catch (NoItemsException ex) {
        }
        Tag toEdit = service.getTagById(id);
        pageModel.addAttribute("tag", toEdit);
        pageModel.addAttribute("tags", allTags);
        pageModel.addAttribute("pages", allPages);
        return "editHashtag";
    }

    @PostMapping("edithashtag")
    public String editHashtag(@Valid Tag toEdit, BindingResult res, Model pageModel) throws InvalidEntityException, InvalidIdException {
        if (res.hasErrors()) {
            List<Tag> allTags = new ArrayList<>();
            List<Page> allPages = new ArrayList<>();
            try {
                allPages = pageService.getAllPages();
            } catch (NoItemsException ex) {
            }
            try {
                allTags = service.getAllTags();
            } catch (NoItemsException ex) {
            }
            pageModel.addAttribute("tag", toEdit);
            pageModel.addAttribute("tags", allTags);
            pageModel.addAttribute("pages", allPages);
            return "editHashtag";
        }

        service.editTag(toEdit);
        return "redirect:/hashtags";
    }

    @GetMapping("deletehashtag")
    public String deleteHashtag(Integer id) throws InvalidIdException {
        service.deleteTag(id);
        return "redirect:/hashtags";
    }

}
