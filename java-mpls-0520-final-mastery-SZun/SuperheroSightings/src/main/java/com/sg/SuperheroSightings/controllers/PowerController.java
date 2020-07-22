/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controllers;

import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.services.PowerService;
import com.sg.SuperheroSightings.services.SuperheroService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class PowerController {

    @Autowired
    PowerService service;
    
    @Autowired
    SuperheroService superService;

    @GetMapping("powers")
    public String displayPowers(Model pageModel) {
        List<Power> allPowers = new ArrayList<>();
        try {
            allPowers = service.getAll();
        } catch (NoItemsException ex) {
        }
        pageModel.addAttribute("powers", allPowers);
        pageModel.addAttribute("power", new Power());
        return "powers";
    }

    @GetMapping("powers/{id}")
    public String displayPowerById(Model pageModel, @PathVariable Integer id) throws InvalidIdException {
        Power toDisplay = service.getPowerById(id);
        List<Superhero> allHeros = new ArrayList<>();
        try {
            allHeros = superService.getAll();
            allHeros = allHeros.stream().filter(s -> s.getPower().getId() == id).collect(Collectors.toList());
        } catch(NoItemsException ex){}
        
        pageModel.addAttribute("power", toDisplay);
        pageModel.addAttribute("heros", allHeros);
        return "powerDetails";
    }

    @PostMapping("powers")
    public String addPower(@Valid Power toAdd, BindingResult result, Model pageModel) throws InvalidEntityException {
        
        try {
            service.getPowerByName(toAdd.getName());
            FieldError error = new FieldError("power", "name", "Name already in use");
            result.addError(error);
        } catch(InvalidNameException | InvalidEntityException ex){}
        
        if (result.hasErrors()) {
            List<Power> allPowers = new ArrayList<>();
            try {
                allPowers = service.getAll();
            } catch (NoItemsException ex) {
            }
            pageModel.addAttribute("powers", allPowers);
            pageModel.addAttribute("power", toAdd);
            return "powers";
        }
        service.addPower(toAdd);
        return "redirect:/powers";
    }

    @GetMapping("editpower")
    public String displayEditPower(Integer id, Model pageModel) throws InvalidIdException {
        Power toEdit = service.getPowerById(id);
        pageModel.addAttribute("power", toEdit);
        return "editPower";
    }

    @PostMapping("editpower")
    public String editPower(@Valid Power toEdit, BindingResult result, Model pageModel) throws InvalidIdException, InvalidEntityException {
        try {
            Power toCheck = service.getPowerByName(toEdit.getName());
            if(!(toCheck.getId() == toEdit.getId())){
                FieldError error = new FieldError("power", "name", "Name already in use");
                result.addError(error);
            }
        } catch(InvalidNameException | InvalidEntityException ex){}
        
        if (result.hasErrors()) {
            pageModel.addAttribute("power", toEdit);
            return "editPower";
        }
        service.editPower(toEdit);
        return "redirect:/powers";
    }

    @GetMapping("deletepower")
    public String deletePower(Integer id) throws InvalidIdException {
        service.deletePowerById(id);
        return "redirect:/powers";
    }

}
