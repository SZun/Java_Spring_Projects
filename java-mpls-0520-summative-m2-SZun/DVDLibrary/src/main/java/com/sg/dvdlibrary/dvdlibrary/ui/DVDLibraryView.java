/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dvdlibrary.ui;

import com.sg.dvdlibrary.dvdlibrary.dto.DVD;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public class DVDLibraryView {

    UserIOConsole io = new UserIOConsole();

    public int getMainMenuChoice() {
        io.print("_______Main Menu_______");
        io.print("1. Create DVD");
        io.print("2. List DVDs");
        io.print("3. View DVD");
        io.print("4. Edit DVD");
        io.print("5. Remove DVD");
        io.print("6. Display DVD by Title");
        io.print("7. Exit");

        int choice = io.readInt("Please enter a selection: ", 1, 7);

        return choice;
    }

    public void displayErrorMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void displayInvalidSelectionMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DVD createNewDVD() {
        DVD toReturn = new DVD();

        toReturn.setTitle(io.readString("Please enter a title for the DVD: "));
        toReturn.setReleaseDate(io.readString("Please enter a release date for the DVD: "));
        toReturn.setMpaaRating(io.readString("Please enter an MPAA rating for the DVD: "));
        toReturn.setDirectorsName(io.readString("Please enter the director's name for the DVD: "));
        toReturn.setStudio(io.readString("Please enter a studio name for the DVD: "));
        toReturn.setNote(io.readString("Please enter a note for the DVD: "));

        return toReturn;
    }

    public void displayDVD(DVD toDisplay) {
        io.print("ID: " + toDisplay.getId());
        io.print("Title: " + toDisplay.getTitle());
        io.print("Release Date: " + toDisplay.getReleaseDate());
        io.print("MPAA Rating: " + toDisplay.getMpaaRating());
        io.print("Director's Name: " + toDisplay.getDirectorsName());
        io.print("Studio: " + toDisplay.getStudio());
        io.print("Note: " + toDisplay.getNote());
    }

    public void displayDVDs(List<DVD> allDVDs) {
        for (DVD d : allDVDs) {
            displayDVD(d);
            System.out.println("");
        }
    }

    public int getDVDId() {
        int toReturn = io.readInt("Please enter DVD id: ", 1, Integer.MAX_VALUE);

        return toReturn;
    }

    public boolean confirmRemove(DVD toConfirm) {
        displayDVD(toConfirm);

        String userResponse = io.readString("Enter \"yes\" to continue: ");

        return userResponse.equalsIgnoreCase("yes");
    }

    public void displaySuccessfullRemoval() {
        io.print("Remove successful.\n");
    }

    public void displayInvalidIdMessage() {
        io.print("Please select a valid id.\n");
    }

    public String getTitle() {
        return io.readString("Please enter the title of the movie: ");
    }

    public void displayInvalidTitleMessage() {
        io.print("Please select a valid title.\n");
    }

    public DVD editDVD(DVD toEdit) {
        DVD updatedDVD = new DVD();

        updatedDVD.setId(toEdit.getId());
        updatedDVD.setTitle(io.editString("Enter a new title or press enter to keep [" + toEdit.getTitle() + "]", toEdit.getTitle()));
        updatedDVD.setReleaseDate(io.editString("Enter a new release date or press enter to keep [" + toEdit.getReleaseDate() + "]", toEdit.getReleaseDate()));
        updatedDVD.setMpaaRating(io.editString("Enter a new MPAA rating or press enter to keep [" + toEdit.getMpaaRating() + "]", toEdit.getMpaaRating()));
        updatedDVD.setDirectorsName(io.editString("Enter a new director's name or press enter to keep [" + toEdit.getDirectorsName() + "]", toEdit.getDirectorsName()));
        updatedDVD.setStudio(io.editString("Enter a new studio or press enter to keep [" + toEdit.getStudio() + "]", toEdit.getStudio()));
        updatedDVD.setNote(io.editString("Enter a new note or press enter to keep [" + toEdit.getNote() + "]", toEdit.getNote()));

        return updatedDVD;

    }

    public void displaySuccessfullEdit() {
        io.print("Edit successful.\n");
    }

}
