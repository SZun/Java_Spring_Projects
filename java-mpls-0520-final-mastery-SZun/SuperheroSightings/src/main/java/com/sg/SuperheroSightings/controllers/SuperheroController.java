/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controllers;

import com.sg.SuperheroSightings.entities.Organization;
import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.entities.Sighting;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.services.OrganizationService;
import com.sg.SuperheroSightings.services.PowerService;
import com.sg.SuperheroSightings.services.SightingService;
import com.sg.SuperheroSightings.services.SuperheroService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class SuperheroController {

    @Autowired
    SuperheroService superService;

    @Autowired
    PowerService powService;

    @Autowired
    OrganizationService orgService;
    
    @Autowired
    SightingService sightService;

    @GetMapping("superheros")
    public String displaySuperheroes(Model pageModel) {
        List<Superhero> allSuperheros = new ArrayList<>();
        List<Power> allPowers = new ArrayList<>();
        List<Organization> allOrganizations = new ArrayList<>();

        try {
            allSuperheros = superService.getAll();
        } catch (NoItemsException ex) {
        }
        try {
            allPowers = powService.getAll();
        } catch (NoItemsException ex) {
        }
        try {
            allOrganizations = orgService.getAll();
        } catch (NoItemsException ex) {
        }

        Superhero hero = new Superhero();
        hero.setPower(new Power());
        hero.setOrganizations(new ArrayList<>());

        pageModel.addAttribute("superheros", allSuperheros);
        pageModel.addAttribute("powers", allPowers);
        pageModel.addAttribute("organizations", allOrganizations);
        pageModel.addAttribute("superhero", hero);
        return "superheros";
    }

    @GetMapping("superheros/{id}")
    public String displaySuperheroById(Model pageModel, @PathVariable Integer id) throws InvalidIdException {
        Superhero toDisplay = superService.getSuperheroById(id);
        List<Sighting> allSightings = new ArrayList<>();
        try {
            allSightings = sightService.getAll();
            allSightings = allSightings.stream().filter(s -> s.getSuperhero().getId() == id).collect(Collectors.toList());
        } catch(NoItemsException ex){}
        pageModel.addAttribute("superhero", toDisplay);
        pageModel.addAttribute("sightings", allSightings);
        return "superheroDetails";
    }

    @PostMapping("superheros")
    public String addSuperhero(@Valid Superhero toAdd, BindingResult result, HttpServletRequest request, Model pageModel) throws InvalidEntityException, InvalidIdException {
        String powerIdStr = request.getParameter("powerId");
        String[] orgIds = request.getParameterValues("orgId");

        if (powerIdStr == null) {
            FieldError error = new FieldError("superhero", "power", "Must include one power");
            result.addError(error);
        }

        try {
            superService.getSuperheroByName(toAdd.getName());
            FieldError error = new FieldError("superhero", "name", "Name already in use");
            result.addError(error);
        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        if (result.hasErrors()) {
            List<Superhero> allSuperheros = new ArrayList<>();
            List<Power> allPowers = new ArrayList<>();
            List<Organization> allOrganizations = new ArrayList<>();

            try {
                allSuperheros = superService.getAll();
            } catch (NoItemsException ex) {
            }
            try {
                allPowers = powService.getAll();
            } catch (NoItemsException ex) {
            }
            try {
                allOrganizations = orgService.getAll();
            } catch (NoItemsException ex) {
            }

            toAdd = setPower(toAdd, powerIdStr);
            toAdd = setOrg(toAdd, orgIds);

            pageModel.addAttribute("superheros", allSuperheros);
            pageModel.addAttribute("powers", allPowers);
            pageModel.addAttribute("organizations", allOrganizations);
            pageModel.addAttribute("superhero", toAdd);
            return "superheros";
        }

        toAdd = setPower(toAdd, powerIdStr);
        toAdd = setOrg(toAdd, orgIds);

        Superhero sup = superService.addSuperhero(new Superhero(toAdd));
        if (orgIds != null) {
            orgService.addMember(toAdd.getOrganizations(), sup.getId());
        }
        return "redirect:/superheros";
    }

    @GetMapping("editsuperhero")
    public String displayEditPower(Integer id, Model pageModel) throws NoItemsException, InvalidIdException {
        Superhero toEdit = superService.getSuperheroById(id);
        List<Power> allPowers = new ArrayList<>();
        List<Organization> superOrgs = new ArrayList<>();
        List<Organization> allOrganizations = new ArrayList<>();

        try {
            allPowers = powService.getAll();
        } catch (NoItemsException ex) {
        }
        try {
            allOrganizations = orgService.getAll();
        } catch (NoItemsException ex) {
        }
        try {
            superOrgs = orgService.getOrganizationsBySuper(id);
        } catch (NoItemsException ex) {
        }

        toEdit.setOrganizations(superOrgs);
        pageModel.addAttribute("superhero", toEdit);
        pageModel.addAttribute("organizations", allOrganizations);
        pageModel.addAttribute("powers", allPowers);

        return "editSuperhero";
    }

    @PostMapping("editsuperhero")
    public String editSuperhero(@Valid Superhero toEdit, BindingResult result, HttpServletRequest request, Model pageModel) throws NoItemsException, InvalidIdException, InvalidEntityException {
        String powerIdStr = request.getParameter("powerId");
        String[] orgIds = request.getParameterValues("orgId");

        if (powerIdStr == null) {
            FieldError error = new FieldError("superhero", "power", "Must include one power");
            result.addError(error);
        }

        try {
            Superhero toCheck = superService.getSuperheroByName(toEdit.getName());
            if (!(toCheck.getId() == toEdit.getId())) {
                FieldError error = new FieldError("superhero", "name", "Name already in use");
                result.addError(error);
            }
        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        if (result.hasErrors()) {
            List<Power> allPowers = new ArrayList<>();
            List<Organization> allOrganizations = new ArrayList<>();

            try {
                allPowers = powService.getAll();
            } catch (NoItemsException ex) {
            }
            try {
                allOrganizations = orgService.getAll();
            } catch (NoItemsException ex) {
            }

            toEdit = setPower(toEdit, powerIdStr);
            toEdit = setOrg(toEdit, orgIds);

            pageModel.addAttribute("superhero", toEdit);
            pageModel.addAttribute("organizations", allOrganizations);
            pageModel.addAttribute("powers", allPowers);

            return "editSuperhero";
        }

        toEdit = setPower(toEdit, powerIdStr);
        toEdit = setOrg(toEdit, orgIds);

        superService.editSuperhero(new Superhero(toEdit));
        if (orgIds != null) {
            orgService.addMember(toEdit.getOrganizations(), toEdit.getId());
        }
        return "redirect:/superheros";
    }

    @GetMapping("deletesuperhero")
    public String deleteSuperhero(Integer id) throws InvalidIdException {
        superService.deleteSuperheroById(id);
        return "redirect:/superheros";
    }

    private Superhero setPower(Superhero toSet, String powerIdStr) throws InvalidIdException {
        if (powerIdStr != null) {
            int id = Integer.parseInt(powerIdStr);
            toSet.setPower(powService.getPowerById(id));
        } else {
            toSet.setPower(new Power());
        }
        return toSet;
    }

    private Superhero setOrg(Superhero toSet, String[] orgIds) throws InvalidIdException {
        if (orgIds != null) {
            List<Organization> orgs = new ArrayList<>();
            for (String id : orgIds) {
                Organization org = orgService.getOrganizationById(Integer.parseInt(id));
                orgs.add(org);
            }
            toSet.setOrganizations(orgs);
        } else {
            toSet.setOrganizations(new ArrayList());
        }

        return toSet;
    }
}
