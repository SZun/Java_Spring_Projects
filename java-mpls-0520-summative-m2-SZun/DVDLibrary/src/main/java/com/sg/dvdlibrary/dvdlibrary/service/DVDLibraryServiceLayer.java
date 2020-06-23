/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dvdlibrary.service;

import com.sg.dvdlibrary.dvdlibrary.dao.DVDLibraryDAO;
import com.sg.dvdlibrary.dvdlibrary.dao.DVDLibraryDAOException;
import com.sg.dvdlibrary.dvdlibrary.dto.DVD;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public class DVDLibraryServiceLayer {

    private DVDLibraryDAO dao;

    public DVDLibraryServiceLayer(DVDLibraryDAO dao) {
        this.dao = dao;
    }

    public DVD createDVD(DVD dvd) throws DVDLibraryDAOException, DVDLibraryDataValidationException, DVDLibraryDuplicateIdException {

        if (dao.getDVDById(dvd.getId()) != null) {
            throw new DVDLibraryDuplicateIdException(
                    "ERROR: Could not create student.  DVD Id "
                    + dvd.getId()
                    + " already exists");
        }

        validateDVDData(dvd);

        dao.addDVD(dvd);

        return dvd;
    }

    public List<DVD> getAllDVDs() throws DVDLibraryDAOException {
        return dao.getAllDVDs();
    }

    public DVD getDVD(int id) throws DVDLibraryDAOException, DVDLibraryInvalidIdException {
        DVD retrieved = dao.getDVDById(id);

        if (retrieved == null) {
            throw new DVDLibraryInvalidIdException("The ID entered was invalid");
        }

        return dao.getDVDById(id);
    }

    public void removeDVD(int id) throws DVDLibraryDAOException {
        dao.deleteDVD(id);
    }

    public void editDVD(DVD updated) throws DVDLibraryDAOException, DVDLibraryDataValidationException, DVDLibraryInvalidIdException  {
        if (dao.getDVDById(updated.getId()) == null) {
            throw new DVDLibraryInvalidIdException(
                    "ERROR: Could not update DVD.  DVD Id "
                    + updated.getId()
                    + " does not exists");
        }

        validateDVDData(updated);

        dao.editDVD(updated);
    }

    public DVD getDVDbyTitle(String title) throws DVDLibraryDAOException, DVDLibraryBlankTitleException {
        DVD retrieved = dao.getDVDbyTitle(title);

        if (retrieved == null) {
            throw new DVDLibraryBlankTitleException("The title entered is invalid");
        }

        return retrieved;
    }

    private void validateDVDData(DVD dvd) throws DVDLibraryDataValidationException {
        if (dvd.getTitle() == null
                || dvd.getTitle().trim().length() == 0
                || dvd.getReleaseDate() == null
                || dvd.getReleaseDate().trim().length() == 0
                || dvd.getMpaaRating() == null
                || dvd.getMpaaRating().trim().length() == 0
                || dvd.getDirectorsName() == null
                || dvd.getDirectorsName().trim().length() == 0
                || dvd.getStudio() == null
                || dvd.getStudio().trim().length() == 0
                || dvd.getNote() == null
                || dvd.getNote().trim().length() == 0) {
            throw new DVDLibraryDataValidationException("ERROR: All fields are required.");
        }
    }

}
