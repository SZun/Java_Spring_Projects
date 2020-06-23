/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dvdlibrary;

import com.sg.dvdlibrary.dvdlibrary.controller.DVDLibraryController;
import com.sg.dvdlibrary.dvdlibrary.dao.DVDLibraryFileDAO;
import com.sg.dvdlibrary.dvdlibrary.service.DVDLibraryServiceLayer;
import com.sg.dvdlibrary.dvdlibrary.ui.DVDLibraryView;

/**
 *
 * @author samg.zun
 */
public class App {

    public static void main(String[] args) {
        DVDLibraryView view = new DVDLibraryView();
        DVDLibraryFileDAO dao = new DVDLibraryFileDAO();
        DVDLibraryServiceLayer service = new DVDLibraryServiceLayer(dao);

        DVDLibraryController controller = new DVDLibraryController(view, service);
        controller.run();
    }

}
