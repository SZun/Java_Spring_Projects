/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.vendingmachine.views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author samg.zun
 */
public class UserIO {

    Scanner scn = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void print(String message) {
        System.out.print(message);
    }

    public String readString(String prompt) {
        print(prompt);
        String userInput = scn.nextLine();

        return userInput;
    }

    public String editString(String prompt, String originalValue) {
        String toReturn = readString(prompt);
        if (toReturn.isEmpty()) {
            toReturn = originalValue;
        }

        return toReturn;
    }

    public int readInt(String prompt) {
        int toReturn = Integer.MIN_VALUE;

        boolean valid = false;
        while (!valid) {
            String userInput = readString(prompt);

            try {
                toReturn = Integer.parseInt(userInput);
                valid = true;
            } catch (NumberFormatException ex) {

            }
        }

        return toReturn;
    }

    public int readInt(String prompt, int incMin, int incMax) {
        int toReturn = Integer.MIN_VALUE;

        boolean valid = false;
        while (!valid) {
            toReturn = readInt(prompt);

            valid = toReturn <= incMax && toReturn >= incMin;
        }

        return toReturn;
    }

    public LocalDate readLocalDate(String prompt) {

        LocalDate toReturn = null;

        boolean validInput = false;

        while (!validInput) {

            String userInput = readString(prompt);

            try {

                //we don't want the user to have to type in something like
                toReturn = LocalDate.parse(
                        userInput,
                        formatter);

                validInput = true;

            } catch (DateTimeParseException ex) {

            }

        }

        return toReturn;
    }

    public LocalDate editDate(String prompt, LocalDate originalData) {
        LocalDate toReturn = originalData;

        boolean validInput = false;

        while (!validInput) {

            String userInput = readString(prompt);

            if (userInput.isEmpty()) {
                validInput = true;
            } else {

                try {

                    //we don't want the user to have to type in something like
                    toReturn = LocalDate.parse(
                            userInput,
                            formatter);

                    validInput = true;

                } catch (DateTimeParseException ex) {

                }
            }

        }

        return toReturn;
    }

}
