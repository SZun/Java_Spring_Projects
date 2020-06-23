/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dvdlibrary.dao;

import com.sg.dvdlibrary.dvdlibrary.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author samg.zun
 */
public class DVDLibraryFileDAO implements DVDLibraryDAO {

    String path = "dvd.txt";

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
        writeFile(allDVDs);

        return toAdd;
    }

    public List<DVD> getAllDVDs() {
        List<DVD> allDVDs = new ArrayList<>();

        try {
            Scanner fileScanner = new Scanner(new BufferedReader(new FileReader(path)));

            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                DVD toAdd = convertLineToDVD(row);

                allDVDs.add(toAdd);
            }
        } catch (FileNotFoundException ex) {
        }

        return allDVDs;
    }

    private void writeFile(List<DVD> allDVDs) throws DVDLibraryDAOException {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(path));

            for (DVD dvd : allDVDs) {
                String line = convertDVDToLine(dvd);
                writer.println(line);
            }

            writer.flush();
            writer.close();

        } catch (IOException ex) {
            throw new DVDLibraryDAOException("Could not open file: " + path, ex);
        }
    }

    private DVD convertLineToDVD(String row) {
        String[] cells = row.split("::");

        DVD toAdd = new DVD();

        toAdd.setId(Integer.parseInt(cells[0]));
        toAdd.setTitle(cells[1]);
        toAdd.setReleaseDate(cells[2]);
        toAdd.setMpaaRating(cells[3]);
        toAdd.setDirectorsName(cells[4]);
        toAdd.setStudio(cells[5]);
        toAdd.setNote(cells[6]);

        return toAdd;
    }

    private String convertDVDToLine(DVD dvd) {
        return dvd.getId() + "::"
                + dvd.getTitle() + "::"
                + dvd.getReleaseDate() + "::"
                + dvd.getMpaaRating() + "::"
                + dvd.getDirectorsName() + "::"
                + dvd.getStudio() + "::"
                + dvd.getNote();
    }

    public DVD getDVDById(int id) {
        DVD toReturn = null;

        List<DVD> allDVDs = getAllDVDs();

        for (DVD d : allDVDs) {
            if (d.getId() == id) {
                toReturn = d;
                break;
            }
        }

        return toReturn;
    }

    public void deleteDVD(int id) throws DVDLibraryDAOException {
        List<DVD> allDVDs = getAllDVDs();

        int index = -1;

        for (int i = 0; i < allDVDs.size(); i++) {
            DVD toCheck = allDVDs.get(i);

            if (toCheck.getId() == id) {
                index = i;
                break;
            }
        }

        allDVDs.remove(index);

        writeFile(allDVDs);
    }

    public DVD getDVDbyTitle(String title) {
        DVD toReturn = null;

        List<DVD> allDVDs = getAllDVDs();

        for (DVD d : allDVDs) {
            if (d.getTitle().equalsIgnoreCase(title)) {
                toReturn = d;
                break;
            }
        }

        return toReturn;
    }

    public void editDVD(DVD updated) throws DVDLibraryDAOException {
        List<DVD> allDVDs = getAllDVDs();

        int index = -1;

        for (int i = 0; i < allDVDs.size(); i++) {
            DVD toCheck = allDVDs.get(i);

            if (toCheck.getId() == updated.getId()) {
                index = i;
                break;
            }
        }

        allDVDs.set(index, updated);
        writeFile(allDVDs);
    }

}
