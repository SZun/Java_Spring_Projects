/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controllers;

import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.entities.Sighting;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.services.LocationService;
import com.sg.SuperheroSightings.services.SightingService;
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
public class LocationController {

    @Autowired
    LocationService service;

    @Autowired
    SightingService sightService;

    @GetMapping("locations")
    public String displayLocations(Model pageModel) {
        List<Location> allLocations = new ArrayList();
        try {
            allLocations = service.getAll();
        } catch (NoItemsException ex) {
        }
        pageModel.addAttribute("locations", allLocations);
        pageModel.addAttribute("location", new Location());
        return "locations";
    }

    @GetMapping("locations/{id}")
    public String displayLocationById(Model pageModel, @PathVariable Integer id) throws InvalidIdException {
        Location toDisplay = service.getLocationById(id);
        List<Sighting> allSightings = new ArrayList<>();
        try {
            allSightings = sightService.getAll();
            allSightings = allSightings.stream().filter(s -> s.getSuperhero().getId() == id).collect(Collectors.toList());
        } catch (NoItemsException ex) {
        }
        pageModel.addAttribute("location", toDisplay);
        pageModel.addAttribute("sightings", allSightings);
        return "locationDetails";
    }

    @PostMapping("locations")
    public String addLocation(@Valid Location toAdd, BindingResult result, Model pageModel) throws InvalidEntityException {
        try {
            service.getLocationByName(toAdd.getName());
            FieldError error = new FieldError("location", "name", "Name already in use");
            result.addError(error);
        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        if (result.hasErrors()) {
            List<Location> allLocations = new ArrayList();
            try {
                allLocations = service.getAll();
            } catch (NoItemsException ex) {
            }
            pageModel.addAttribute("locations", allLocations);
            pageModel.addAttribute("location", toAdd);
            return "locations";
        }

        service.addLocation(toAdd);

        return "redirect:/locations";
    }

    @GetMapping("editlocation")
    public String displayEditLocation(Integer id, Model pageModel) throws InvalidIdException {
        Location toEdit = service.getLocationById(id);
        pageModel.addAttribute("location", toEdit);
        return "editLocation";
    }

    @PostMapping("editlocation")
    public String editLocation(@Valid Location toEdit, BindingResult result, Model pageModel) throws InvalidEntityException, InvalidIdException {
        try {
            Location toCheck = service.getLocationByName(toEdit.getName());
            if (!(toCheck.getId() == toEdit.getId())) {
                FieldError error = new FieldError("location", "name", "Name already in use");
                result.addError(error);
            }
        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        if (result.hasErrors()) {
            pageModel.addAttribute("location", toEdit);
            return "editLocation";
        }
        service.editLocation(toEdit);
        return "redirect:/locations";
    }

    @GetMapping("deletelocation")
    public String deleteLocation(Integer id) throws InvalidIdException {
        service.deleteLocationById(id);
        return "redirect:/locations";
    }

}
