/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controllers;

import com.sg.SuperheroSightings.entities.Organization;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.services.OrganizationService;
import com.sg.SuperheroSightings.services.SuperheroService;
import java.util.ArrayList;
import java.util.List;
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
public class OrganizationController {

    @Autowired
    OrganizationService orgService;

    @Autowired
    SuperheroService superService;

    @GetMapping("organizations")
    public String displayOrganizations(Model pageModel) {
        List<Organization> allOrganizations = new ArrayList<>();
        try {
            allOrganizations = orgService.getAll();
        } catch (NoItemsException ex) {
        }
        List<Superhero> allSuperheros = new ArrayList<>();
        try {
            allSuperheros = superService.getAll();
        } catch (NoItemsException ex) {
        }
        Organization org = new Organization();
        org.setMembers(new ArrayList<>());

        pageModel.addAttribute("organizations", allOrganizations);
        pageModel.addAttribute("superheros", allSuperheros);
        pageModel.addAttribute("organization", org);

        return "organizations";
    }

    @GetMapping("organizations/{id}")
    public String displayOrganizationById(Model pageModel, @PathVariable Integer id) throws InvalidIdException {
        Organization toDisplay = orgService.getOrganizationById(id);
        pageModel.addAttribute("organization", toDisplay);
        return "organizationDetails";
    }

    @PostMapping("organizations")
    public String addOrganization(@Valid Organization toAdd, BindingResult result, HttpServletRequest request, Model pageModel) throws InvalidEntityException, InvalidIdException {
        String[] memberIds = request.getParameterValues("heroId");
        toAdd = setMembers(toAdd, memberIds);

        try {
            orgService.getOrganizationByName(toAdd.getName());
            FieldError error = new FieldError("organization", "name", "Name already in use");
            result.addError(error);
        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        if (result.hasErrors()) {
            List<Organization> allOrganizations = new ArrayList<>();
            try {
                allOrganizations = orgService.getAll();
            } catch (NoItemsException ex) {
            }
            List<Superhero> allSuperheros = new ArrayList<>();
            try {
                allSuperheros = superService.getAll();
            } catch (NoItemsException ex) {
            }

            toAdd = setMembers(toAdd, memberIds);

            pageModel.addAttribute("organizations", allOrganizations);
            pageModel.addAttribute("superheros", allSuperheros);
            pageModel.addAttribute("organization", toAdd);
            return "organizations";
        }

        orgService.addOrganization(toAdd);
        return "redirect:/organizations";
    }

    @GetMapping("editorganization")
    public String displayEditOrganization(Integer id, Model pageModel) throws InvalidIdException {
        Organization toEdit = orgService.getOrganizationById(id);
        List<Superhero> allSuperheros = new ArrayList<>();
        try {
            allSuperheros = superService.getAll();
            allSuperheros.stream().forEach(s -> s.setOrganizations(null));
        } catch (NoItemsException ex) {
        }

        pageModel.addAttribute("superheros", allSuperheros);
        pageModel.addAttribute("organization", toEdit);
        return "editOrganization";
    }

    @PostMapping("editorganization")
    public String editOrganization(@Valid Organization toEdit, BindingResult result, HttpServletRequest request, Model pageModel) throws InvalidIdException, InvalidEntityException {
        String[] memberIds = request.getParameterValues("heroId");
        toEdit = setMembers(toEdit, memberIds);

        try {
            Organization toCheck = orgService.getOrganizationByName(toEdit.getName());
            if (!(toCheck.getId() == toEdit.getId())) {
                FieldError error = new FieldError("organization", "name", "Name already in use");
                result.addError(error);
            }
        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        if (result.hasErrors()) {
            List<Superhero> allSuperheros = new ArrayList<>();
            try {
                allSuperheros = superService.getAll();
            } catch (NoItemsException ex) {
            }

            pageModel.addAttribute("superheros", allSuperheros);
            pageModel.addAttribute("organization", toEdit);

            return "editOrganization";
        }

        orgService.editOrganization(toEdit);
        return "redirect:/organizations";
    }

    @GetMapping("deleteorganization")
    public String deleteOrganization(Integer id) throws InvalidIdException {
        orgService.deleteOrganizationById(id);
        return "redirect:/organizations";
    }

    private Organization setMembers(Organization toSet, String[] memberIds) throws InvalidIdException {
        if (memberIds != null) {
            List<Superhero> members = new ArrayList<>();
            for (String id : memberIds) {
                Superhero member = superService.getSuperheroById(Integer.parseInt(id));
                members.add(member);
            }
            toSet.setMembers(members);
        } else {
            toSet.setMembers(new ArrayList());
        }

        return toSet;
    }

}
