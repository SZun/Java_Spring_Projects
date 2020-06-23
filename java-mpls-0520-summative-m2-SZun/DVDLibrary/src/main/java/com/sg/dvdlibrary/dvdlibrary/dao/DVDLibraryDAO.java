/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dvdlibrary.dao;

import com.sg.dvdlibrary.dvdlibrary.dto.DVD;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface DVDLibraryDAO {

    public DVD addDVD(DVD toAdd) throws DVDLibraryDAOException;

    public List<DVD> getAllDVDs();

    public DVD getDVDById(int id);

    public void deleteDVD(int id) throws DVDLibraryDAOException;

    public DVD getDVDbyTitle(String title);

    public void editDVD(DVD updated) throws DVDLibraryDAOException;
}
