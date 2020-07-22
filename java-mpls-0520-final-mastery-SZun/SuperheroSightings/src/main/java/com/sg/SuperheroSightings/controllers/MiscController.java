/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controllers;

import com.sg.SuperheroSightings.entities.Sighting;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.services.SightingService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author samg.zun
 */
@Controller
public class MiscController {

    @Autowired
    SightingService service;

    @GetMapping("/")
    public String diplsayIndex(Model pageModel) {

        List<Sighting> allSightings = new ArrayList<>();
        List<Sighting> lastTen = new ArrayList<>();

        try {
            allSightings = bubbleSort(service.getAll());

            for (int i = 0; i < allSightings.size(); i++) {
                if (i != 10) {
                    lastTen.add(allSightings.get(i));
                } else {
                    break;
                }
            }
        } catch (NoItemsException ex) {
        }

        pageModel.addAttribute("sightings", lastTen);

        return "index";
    }

    @GetMapping("error")
    public String diplsayError() {
        return "error";
    }

    private List<Sighting> bubbleSort(List<Sighting> s) {
        int n = s.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (s.get(j).getSightingDate().isBefore(s.get(j + 1).getSightingDate())) {
                    Sighting temp = s.get(j);
                    s.set(j, s.get(j + 1));
                    s.set(j + 1, temp);
                }
            }
        }

        return s;
    }

}
