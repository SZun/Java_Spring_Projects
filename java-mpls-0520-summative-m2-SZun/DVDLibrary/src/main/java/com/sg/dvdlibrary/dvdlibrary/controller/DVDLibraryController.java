/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dvdlibrary.controller;

import com.sg.dvdlibrary.dvdlibrary.dao.DVDLibraryDAOException;
import com.sg.dvdlibrary.dvdlibrary.dto.DVD;
import com.sg.dvdlibrary.dvdlibrary.service.DVDLibraryBlankTitleException;
import com.sg.dvdlibrary.dvdlibrary.service.DVDLibraryDataValidationException;
import com.sg.dvdlibrary.dvdlibrary.service.DVDLibraryDuplicateIdException;
import com.sg.dvdlibrary.dvdlibrary.service.DVDLibraryInvalidIdException;
import com.sg.dvdlibrary.dvdlibrary.service.DVDLibraryServiceLayer;
import com.sg.dvdlibrary.dvdlibrary.ui.DVDLibraryView;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public class DVDLibraryController {

    private DVDLibraryView view;
    private DVDLibraryServiceLayer service;

    public DVDLibraryController(DVDLibraryView view, DVDLibraryServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {

        boolean done = false;

        while (!done) {
            int mainMenuChoice = view.getMainMenuChoice();

            try {
                switch (mainMenuChoice) {
                    case 1:
                        createDVD();
                        break;
                    case 2:
                        displayAllDVDs();
                        break;
                    case 3:
                        displaySingleDVD();
                        break;
                    case 4:
                        editDVD();
                        break;
                    case 5:
                        removeDVD();
                        break;
                    case 6:
                        displayDVDByTitle();
                        break;
                    case 7:
                        done = true;
                        break;
                    default:
                        view.displayInvalidSelectionMessage();
                        break;

                }

            } catch (DVDLibraryBlankTitleException ex) {
                view.displayErrorMessage(ex.getMessage());
            } catch (DVDLibraryInvalidIdException ex) {
                view.displayErrorMessage(ex.getMessage());
            } catch (DVDLibraryDAOException ex) {
                view.displayErrorMessage(ex.getMessage());
            } catch (DVDLibraryDataValidationException ex) {
                view.displayErrorMessage(ex.getMessage());
            } catch (DVDLibraryDuplicateIdException ex) {
                view.displayErrorMessage(ex.getMessage());
            }

        }

    }

    ;

    private void createDVD() throws DVDLibraryDAOException, DVDLibraryDataValidationException, DVDLibraryDuplicateIdException {
        DVD toAdd = view.createNewDVD();

        DVD added = service.createDVD(toAdd);

        view.displayDVD(added);
    }

    private void displayAllDVDs() throws DVDLibraryDAOException {
        List<DVD> allDVDs = service.getAllDVDs();

        view.displayDVDs(allDVDs);
    }

    private void editDVD() throws DVDLibraryDAOException, DVDLibraryDataValidationException, DVDLibraryDuplicateIdException, DVDLibraryInvalidIdException {
        int id = view.getDVDId();

        DVD retrieved = service.getDVD(id);

        if (retrieved != null) {
            DVD updated = view.editDVD(retrieved);

            service.editDVD(updated);

            view.displaySuccessfullEdit();
        } else {
            view.displayInvalidIdMessage();
        }
    }

    private void removeDVD() throws DVDLibraryDAOException, DVDLibraryInvalidIdException {
        int id = view.getDVDId();

        DVD retrieved = service.getDVD(id);

        if (retrieved != null) {

            boolean confirm = view.confirmRemove(retrieved);

            if (confirm) {
                service.removeDVD(id);
                view.displaySuccessfullRemoval();
            }

        } else {
            view.displayInvalidIdMessage();
        }
    }

    private void displaySingleDVD() throws DVDLibraryDAOException, DVDLibraryInvalidIdException {
        int id = view.getDVDId();

        DVD retrieved = service.getDVD(id);

        if (retrieved != null) {
            view.displayDVD(retrieved);
        } else {
            view.displayInvalidIdMessage();
        }
    }

    private void displayDVDByTitle() throws DVDLibraryDAOException, DVDLibraryBlankTitleException {
        String title = view.getTitle();

        DVD retrieved = service.getDVDbyTitle(title);

        if (retrieved != null) {
            view.displayDVD(retrieved);
        } else {
            view.displayInvalidTitleMessage();
        }
    }

}
