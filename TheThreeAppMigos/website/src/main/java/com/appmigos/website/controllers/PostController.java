/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.controllers;

import com.appmigos.website.dtos.Page;
import com.appmigos.website.dtos.Post;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.service.PostService;
import com.appmigos.website.service.TagService;
import com.appmigos.website.service.StaticPageService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author samg.zun
 */
@Controller
public class PostController {

    @Autowired
    PostService service;

    @Autowired
    StaticPageService pageService;

    @Autowired
    TagService hashService;

    @GetMapping("blogs")
    public String displayBlogs(Model pageModel) {
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
            allTags = hashService.getAllTags();
        } catch (NoItemsException ex) {
        }
        pageModel.addAttribute("blogs", allBlogs);
        pageModel.addAttribute("pages", allPages);
        pageModel.addAttribute("tags", allTags);
        return "blogs";
    }

    @GetMapping("tagged-blogs")
    public String displayAllBlogsByTagId(Integer id, Model pageModel) {
        List<Post> allBlogs = new ArrayList<>();
        List<Page> allPages = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        try {
            allBlogs = service.getPostsByTag(id);
        } catch (NoItemsException | InvalidIdException ex) {
        }
        try {
            allPages = pageService.getAllPages();
        } catch (NoItemsException ex) {
        }
        try {
            allTags = hashService.getAllTags();
        } catch (NoItemsException ex) {
        }
        pageModel.addAttribute("blogs", allBlogs);
        pageModel.addAttribute("pages", allPages);
        pageModel.addAttribute("tags", allTags);
        return "blogs";
    }

    @GetMapping("blog/{id}")
    public String displayBlogDetail(@PathVariable Integer id, Model pageModel) throws InvalidIdException {
        Post toDisplay = service.getPostById(id);
        List<Page> allPages = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        try {
            allTags = hashService.getAllTags();
        } catch (NoItemsException ex) {
        }
        try {
            allPages = pageService.getAllPages();
        } catch (NoItemsException ex) {
        }
        pageModel.addAttribute("pages", allPages);
        pageModel.addAttribute("blog", toDisplay);
        pageModel.addAttribute("tags", allTags);
        return "blogDetail";
    }

    @GetMapping("blog-create")
    public String displayAddBlog(Model pageModel) throws InvalidIdException {
        Post blog = new Post();
        blog.setTags(new HashSet<>());
        List<Tag> hashtags = new ArrayList<>();
        List<Page> allPages = new ArrayList<>();
        try {
            allPages = pageService.getAllPages();
        } catch (NoItemsException ex) {
        }
        try {
            hashtags = hashService.getAllTags();
        } catch (NoItemsException ex) {
        }
        pageModel.addAttribute("blog", blog);
        pageModel.addAttribute("post", blog);
        pageModel.addAttribute("pages", allPages);
        pageModel.addAttribute("hashtags", hashtags);

        return "addBlog";
    }

    @PostMapping("addblog")
    public String addBlog(@Valid Post toAdd, BindingResult result, HttpServletRequest req, Model pageModel) throws InvalidEntityException, InvalidIdException {
        String[] hashtagIds = req.getParameterValues("hashId");

        boolean validStartDate = toAdd.getStartDateString() != null && toAdd.getStartDateString().matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")
                && toAdd.getStartDateString().trim().length() == 10;
        boolean validEndDate = toAdd.getStartDateString() != null && toAdd.getStartDateString().matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")
                && toAdd.getStartDateString().trim().length() == 10;

        if (validStartDate) {
            if (LocalDate.now().compareTo(LocalDate.parse(toAdd.getStartDateString())) == 0
                    || LocalDate.parse(toAdd.getStartDateString()).isAfter(LocalDate.now())) {
                toAdd.setStart(LocalDate.parse(toAdd.getStartDateString()));
            } else {
                FieldError error = new FieldError("post", "startDateString", "Date must be today or in future");
                result.addError(error);
            }

        }

        if (validEndDate) {
            if (LocalDate.parse(toAdd.getEndDateString()).isAfter(LocalDate.now())) {
                toAdd.setEnd(LocalDate.parse(toAdd.getEndDateString()));
            } else {
                FieldError error = new FieldError("post", "endDateString", "Date must be in future");
                result.addError(error);
            }

        }

        if (validStartDate && validEndDate
                && (LocalDate.parse(toAdd.getEndDateString()).isBefore(LocalDate.parse(toAdd.getStartDateString())) || 
                LocalDate.parse(toAdd.getEndDateString()).compareTo(LocalDate.parse(toAdd.getStartDateString())) == 0 )) {
            FieldError error = new FieldError("post", "endDateString", "End date must be after Start Date");
            result.addError(error);
        }

        if (hashtagIds == null) {
            FieldError error = new FieldError("post", "tags", "Must include at least one hashtag");
            result.addError(error);
        } else {
            toAdd = setTags(toAdd, hashtagIds);
        }

        if (result.hasErrors()) {
            toAdd = setTags(toAdd, hashtagIds);

            List<Tag> hashtags = new ArrayList<>();
            List<Page> allPages = new ArrayList<>();
            try {
                hashtags = hashService.getAllTags();
            } catch (NoItemsException ex) {
            }
            try {
                allPages = pageService.getAllPages();
            } catch (NoItemsException ex) {
            }
            pageModel.addAttribute("blog", toAdd);
            pageModel.addAttribute("pages", allPages);
            pageModel.addAttribute("hashtags", hashtags);
            return "addBlog";
        }

        service.createPost(toAdd);

        return "redirect:/blogs";
    }

    @GetMapping("blog-edit")
    public String displayEditBlog(Integer id, Model pageModel) throws InvalidIdException {
        Post toEdit = service.getPostById(id);
        toEdit.setStartDateString(toEdit.getStart().toString());
        toEdit.setEndDateString(toEdit.getEnd().toString());
        List<Tag> hashtags = new ArrayList<>();
        List<Page> allPages = new ArrayList<>();
        try {
            hashtags = hashService.getAllTags();
        } catch (NoItemsException ex) {
        }
        try {
            allPages = pageService.getAllPages();
        } catch (NoItemsException ex) {
        }
        pageModel.addAttribute("blog", toEdit);
        pageModel.addAttribute("pages", allPages);
        pageModel.addAttribute("post", toEdit);
        pageModel.addAttribute("hashtags", hashtags);

        return "editBlog";
    }

    @PostMapping("editblog")
    public String editBlog(@Valid Post toEdit, BindingResult result, HttpServletRequest req, Model pageModel) throws InvalidIdException, InvalidEntityException {
        String[] hashtagIds = req.getParameterValues("hashId");
        
        boolean validStartDate = toEdit.getStartDateString() != null && toEdit.getStartDateString().matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")
                && toEdit.getStartDateString().trim().length() == 10;
        boolean validEndDate = toEdit.getStartDateString() != null && toEdit.getStartDateString().matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")
                && toEdit.getStartDateString().trim().length() == 10;

        if (validStartDate) {
            if (LocalDate.now().compareTo(LocalDate.parse(toEdit.getStartDateString())) == 0
                    || LocalDate.parse(toEdit.getStartDateString()).isAfter(LocalDate.now())) {
                toEdit.setStart(LocalDate.parse(toEdit.getStartDateString()));
            } else {
                FieldError error = new FieldError("post", "startDateString", "Date must be today or in future");
                result.addError(error);
            }

        }

        if (validEndDate) {
            if (LocalDate.parse(toEdit.getEndDateString()).isAfter(LocalDate.now())) {
                toEdit.setEnd(LocalDate.parse(toEdit.getEndDateString()));
            } else {
                FieldError error = new FieldError("post", "endDateString", "Date must or in future");
                result.addError(error);
            }

        }
        
        if (validStartDate && validEndDate
                && (LocalDate.parse(toEdit.getEndDateString()).isBefore(LocalDate.parse(toEdit.getStartDateString())) || 
                LocalDate.parse(toEdit.getEndDateString()).compareTo(LocalDate.parse(toEdit.getStartDateString())) == 0 )) {
            FieldError error = new FieldError("post", "endDateString", "End date must be after Start Date");
            result.addError(error);
        }

        if (hashtagIds == null) {
            FieldError error = new FieldError("blog", "hashtags", "Must include at least one hashtag");
            result.addError(error);
        } else {
            toEdit = setTags(toEdit, hashtagIds);
        }

        if (result.hasErrors()) {
            toEdit = setTags(toEdit, hashtagIds);
            List<Tag> hashtags = new ArrayList<>();
            List<Page> allPages = new ArrayList<>();
            try {
                hashtags = hashService.getAllTags();
            } catch (NoItemsException ex) {
            }
            try {
                allPages = pageService.getAllPages();
            } catch (NoItemsException ex) {
            }
            pageModel.addAttribute("blog", toEdit);
            pageModel.addAttribute("pages", allPages);
            pageModel.addAttribute("hashtags", hashtags);
            return "editBlog";
        }

        service.editPost(toEdit);
        return "redirect:/blogs";
    }

    @GetMapping("deleteblog")
    public String deletePostById(Integer id) throws InvalidIdException {
        service.deletePost(id);
        return "redirect:/blogs";
    }

    private Post setTags(Post toAdd, String[] hashtagIds) throws InvalidIdException {
        Set<Tag> hashtags = new HashSet<Tag>();
        if (hashtagIds != null) {
            for (String idStr : hashtagIds) {
                int id = Integer.parseInt(idStr);
                Tag ht = hashService.getTagById(id);
                hashtags.add(ht);
            }
        }
        toAdd.setTags(hashtags);
        return toAdd;
    }

}
