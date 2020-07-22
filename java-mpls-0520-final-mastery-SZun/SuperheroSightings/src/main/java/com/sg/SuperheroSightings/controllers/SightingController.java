/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controllers;

import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.entities.Sighting;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.services.LocationService;
import com.sg.SuperheroSightings.services.SightingService;
import com.sg.SuperheroSightings.services.SuperheroService;
import java.time.LocalDate;
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
public class SightingController {

    @Autowired
    SightingService sightService;

    @Autowired
    LocationService locService;

    @Autowired
    SuperheroService superService;

    @GetMapping("sightings")
    public String displaySightings(Model pageModel) {
        List<Sighting> allSightings = new ArrayList<>();
        List<Location> allLocations = new ArrayList<>();
        List<Superhero> allSuperheros = new ArrayList<>();

        try {
            allSightings = sightService.getAll();
        } catch (NoItemsException ex) {
        }
        try {
            allLocations = locService.getAll();
        } catch (NoItemsException ex) {
        }
        try {
            allSuperheros = superService.getAll();
        } catch (NoItemsException ex) {
        }

        Sighting sight = new Sighting();
        sight.setLocation(new Location());
        sight.setSuperhero(new Superhero());

        pageModel.addAttribute("sightings", allSightings);
        pageModel.addAttribute("locations", allLocations);
        pageModel.addAttribute("superheros", allSuperheros);
        pageModel.addAttribute("sighting", sight);
        return "sightings";
    }

    @GetMapping("sightings/{id}")
    public String displaySightingById(Model pageModel, @PathVariable Integer id) throws InvalidIdException {
        Sighting toDisplay = sightService.getSightingById(id);
        pageModel.addAttribute("sighting", toDisplay);
        return "sightingDetails";
    }

    @PostMapping("sightings")
    public String addSighting(@Valid Sighting toAdd, BindingResult result, HttpServletRequest request, Model pageModel) throws InvalidEntityException, InvalidIdException {
        String locationIdStr = request.getParameter("locationId");
        String heroIdStr = request.getParameter("heroId");
        String sDateStr = request.getParameter("sightingDateStr");

        if (sDateStr != null && sDateStr.matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")
                && toAdd.getSightingDateStr().trim().length() == 10) {
            if (LocalDate.parse(sDateStr).isBefore(LocalDate.now())) {
                toAdd.setSightingDate(LocalDate.parse(sDateStr));
            } else {
                FieldError error = new FieldError("sighting", "sightingDateStr", "Date must be in past");
                result.addError(error);
            }

        }

        if (locationIdStr == null) {
            FieldError error = new FieldError("sighting", "location", "Must include one location");
            result.addError(error);
        } else {
            toAdd = setLocation(toAdd, locationIdStr);
        }

        if (heroIdStr == null) {
            FieldError error = new FieldError("sighting", "superhero", "Must include one superhero");
            result.addError(error);
        } else {
            toAdd = setSuperhero(toAdd, heroIdStr);
        }

        if (result.hasErrors()) {
            List<Sighting> allSightings = new ArrayList<>();
            List<Location> allLocations = new ArrayList<>();
            List<Superhero> allSuperheros = new ArrayList<>();

            try {
                allSightings = sightService.getAll();
            } catch (NoItemsException ex) {
            }
            try {
                allLocations = locService.getAll();
            } catch (NoItemsException ex) {
            }
            try {
                allSuperheros = superService.getAll();
            } catch (NoItemsException ex) {
            }

            toAdd = setLocation(toAdd, locationIdStr);
            toAdd = setSuperhero(toAdd, heroIdStr);

            pageModel.addAttribute("sightings", allSightings);
            pageModel.addAttribute("locations", allLocations);
            pageModel.addAttribute("superheros", allSuperheros);
            pageModel.addAttribute("sighting", toAdd);

            return "sightings";
        }

        toAdd = setLocation(toAdd, locationIdStr);
        toAdd = setSuperhero(toAdd, heroIdStr);

        sightService.addSighting(toAdd);
        return "redirect:/sightings";
    }

    @GetMapping("editsighting")
    public String displayEditSighting(Integer id, Model pageModel) throws InvalidIdException, NoItemsException {
        Sighting toEdit = sightService.getSightingById(id);
        toEdit.setSightingDateStr(toEdit.getSightingDate().toString());
        toEdit.getSuperhero().setOrganizations(null);
        List<Location> allLocations = new ArrayList<>();
        List<Superhero> allSuperheros = new ArrayList<>();
        
            try {
                allLocations = locService.getAll();
            } catch (NoItemsException ex) {
            }
            try {
                allSuperheros = superService.getAll();
                allSuperheros.forEach(s -> s.setOrganizations(null));
            } catch (NoItemsException ex) {
            }
        
        pageModel.addAttribute("sighting", toEdit);
        pageModel.addAttribute("locations", allLocations);
        pageModel.addAttribute("superheros", allSuperheros);
        return "editSighting";
    }

    @PostMapping("editsighting")
    public String editSighting(@Valid Sighting toEdit, BindingResult result, HttpServletRequest request, Model pageModel) throws InvalidEntityException, InvalidIdException {
        String locationIdStr = request.getParameter("locationId");
        String heroIdStr = request.getParameter("heroId");
        String sDateStr = request.getParameter("sightingDateStr");

        if (sDateStr != null && sDateStr.matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")
                && toEdit.getSightingDateStr().trim().length() == 10) {
            if (LocalDate.parse(sDateStr).isBefore(LocalDate.now())) {
                toEdit.setSightingDate(LocalDate.parse(sDateStr));
            } else {
                FieldError error = new FieldError("sighting", "sightingDateStr", "Date must be in past");
                result.addError(error);
            }
        }

        if (locationIdStr == null) {
            FieldError error = new FieldError("sighting", "location", "Must include one location");
            result.addError(error);
        } else {
            toEdit = setLocation(toEdit, locationIdStr);
        }

        if (heroIdStr == null) {
            FieldError error = new FieldError("sighting", "superhero", "Must include one superhero");
            result.addError(error);
        } else {
            toEdit = setSuperhero(toEdit, heroIdStr);
        }

        if (result.hasErrors()) {
            List<Location> allLocations = new ArrayList<>();
            List<Superhero> allSuperheros = new ArrayList<>();
            try {
                allLocations = locService.getAll();
            } catch (NoItemsException ex) {
            }
            try {
                allSuperheros = superService.getAll();
            } catch (NoItemsException ex) {
            }

            toEdit = setLocation(toEdit, locationIdStr);
            toEdit = setSuperhero(toEdit, heroIdStr);

            pageModel.addAttribute("sighting", toEdit);
            pageModel.addAttribute("locations", allLocations);
            pageModel.addAttribute("superheros", allSuperheros);

            return "editSighting";
        }

        toEdit = setLocation(toEdit, locationIdStr);
        toEdit = setSuperhero(toEdit, heroIdStr);

        sightService.editSighting(toEdit);
        return "redirect:/sightings";
    }

    @GetMapping("deletesighting")
    public String deleteSighting(Integer id) throws InvalidIdException {
        sightService.deleteSightingById(id);
        return "redirect:/sightings";
    }

    private Sighting setLocation(Sighting toSet, String locationIdStr) throws InvalidIdException {
        if (locationIdStr != null) {
            int id = Integer.parseInt(locationIdStr);
            toSet.setLocation(locService.getLocationById(id));

        } else {
            toSet.setLocation(new Location());
        }
        return toSet;
    }

    private Sighting setSuperhero(Sighting toSet, String heroIdStr) throws InvalidIdException {
        if (heroIdStr != null) {
            int id = Integer.parseInt(heroIdStr);
            toSet.setSuperhero(superService.getSuperheroById(id));
        } else {
            toSet.setSuperhero(new Superhero());
        }
        return toSet;
    }

}
