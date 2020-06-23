/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dvdlibrary.dao;

import com.sg.dvdlibrary.dvdlibrary.dao.DVDLibraryDAO;
import com.sg.dvdlibrary.dvdlibrary.dao.DVDLibraryDAOException;
import com.sg.dvdlibrary.dvdlibrary.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author samg.zun
 */
public class DVDLibraryInMemDAO implements  DVDLibraryDAO {
    
    List<DVD> allDVDs = new ArrayList<>();

    @Override
    public DVD addDVD(DVD toAdd) throws DVDLibraryDAOException {
        List<DVD> allDVDs = getAllDVDs();

        int maxId = 0;

        for (DVD d : allDVDs) {
            if (d.getId() > maxId) {
                maxId = d.getId();
            }
        }

        toAdd.setId(maxId + 1);

        allDVDs.add(toAdd);

        return toAdd;
    }

    @Override
    public List<DVD> getAllDVDs() {
        return allDVDs;
    }

    @Override
    public DVD getDVDById(int id) {
        DVD toReturn = null;

        for (DVD d : allDVDs) {
            if (d.getId() == id) {
                toReturn = d;
                break;
            }
        }

        return toReturn;
    }

    @Override
    public void deleteDVD(int id) throws DVDLibraryDAOException {
        int index = -1;

        for (int i = 0; i < allDVDs.size(); i++) {
            DVD toCheck = allDVDs.get(i);

            if (toCheck.getId() == id) {
                index = i;
                break;
            }
        }

        allDVDs.remove(index);
    }

    @Override
    public DVD getDVDbyTitle(String title) {
        DVD toReturn = null;

        for (DVD d : allDVDs) {
            if (d.getTitle().equalsIgnoreCase(title)) {
                toReturn = d;
                break;
            }
        }

        return toReturn;
    }

    @Override
    public void editDVD(DVD updated) throws DVDLibraryDAOException {
        int index = -1;

        for (int i = 0; i < allDVDs.size(); i++) {
            DVD toCheck = allDVDs.get(i);

            if (toCheck.getId() == updated.getId()) {
                index = i;
                break;
            }
        }

        allDVDs.set(index, updated);
    }
    
}
