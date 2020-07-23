/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.controllers;

import com.appmigos.website.dtos.Page;
import com.appmigos.website.dtos.Post;
import com.appmigos.website.dtos.Role;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.dtos.User;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.service.PostService;
import com.appmigos.website.service.RoleService;
import com.appmigos.website.service.StaticPageService;
import com.appmigos.website.service.TagService;
import com.appmigos.website.service.UserService;
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
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Isaia
 */
@Controller
public class AdminController {

    @Autowired
    PostService service;

    @Autowired
    StaticPageService pageService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
    
    @Autowired
    TagService tagService;

    @GetMapping("admin")
    public String displayAdminPage(Model pageModel) {
        List<Post> unnaprovedBlogs = new ArrayList<>();
        List<Page> allPages = new ArrayList<>();
        List<User> allUsers = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        try {
            unnaprovedBlogs = service.getAll(false);
        } catch (NoItemsException ex) {
        }
        try {
            allPages = pageService.getAllPages();
        } catch (NoItemsException ex) {
        }
        try {
            allUsers = userService.getAll();
        } catch (NoItemsException ex) {
        }
        try {
            allTags = tagService.getAllTags();
        } catch (NoItemsException ex) {
        }

        pageModel.addAttribute("blogs", unnaprovedBlogs);
        pageModel.addAttribute("pages", allPages);
        pageModel.addAttribute("users", allUsers);
        pageModel.addAttribute("tags", allTags);
        return "admin";
    }

    @GetMapping("approveblog")
    public String approveBlog(Integer id, Model pageModel) throws InvalidIdException, InvalidEntityException {
        service.approvePost(id);
        return "redirect:/admin";
    }

    @GetMapping("deleteuser")
    public String deleteUserById(Integer id) throws InvalidIdException {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("edit-user")
    public String displayEditUser(Integer id, Model pageModel) throws InvalidIdException {
        List<Page> allPages = new ArrayList<>();
        List<Role> allRoles = new ArrayList<>();
        List<Tag> allTags = new ArrayList<>();
        User toEdit = userService.getById(id);
        try {
            allPages = pageService.getAllPages();
        } catch (NoItemsException ex) {
        }
        try {
            allRoles = roleService.getAll();
        } catch (NoItemsException ex) {
        }
        try {
            allTags = tagService.getAllTags();
        } catch (NoItemsException ex) {
        }
        pageModel.addAttribute("pages", allPages);
        pageModel.addAttribute("user", toEdit);
        pageModel.addAttribute("roles", allRoles);
        pageModel.addAttribute("tags", allTags);
        return "editUser";
    }

    @PostMapping("edituser")
    public String editUser(@Valid User toEdit, BindingResult result, HttpServletRequest req, Model pageModel) throws InvalidIdException, InvalidEntityException {
        String roleIdStr = req.getParameter("roleId");

        toEdit = setRole(toEdit, roleIdStr);

        if (roleIdStr == null) {
            FieldError error = new FieldError("user", "role", "Must include one role");
            result.addError(error);
        }

        if (result.hasErrors()) {
            List<Page> allPages = new ArrayList<>();
            List<Role> allRoles = new ArrayList<>();
            try {
                allPages = pageService.getAllPages();
            } catch (NoItemsException ex) {
            }
            try {
                allRoles = roleService.getAll();
            } catch (NoItemsException ex) {
            }
            pageModel.addAttribute("pages", allPages);
            pageModel.addAttribute("user", toEdit);
            pageModel.addAttribute("roles", allRoles);
            return "editUser";
        }
        
        userService.editUser(toEdit);
        return "redirect:/admin";
    }

    private User setRole(User toSet, String roleIdStr) throws InvalidIdException {
        Set<Role> roles = new HashSet<>();
        if (roleIdStr != null) {
                int id = Integer.parseInt(roleIdStr);
                roles.add(roleService.getById(id));
        }
        toSet.setRole(roles);
        return toSet;
    }

}
