/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author samg.zun
 */
public class UserIO {
    static Scanner scn = new Scanner(System.in);

    public static void print(String message) {
        System.out.print(message);
    }
    
    public static String readString(String prompt) {
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

    public static int readInt(String prompt) {

        boolean validInput = false;
        int userValue = -1;

        while (!validInput) {
            print(prompt);
            String userInput = scn.nextLine();

            try {

                userValue = Integer.parseInt(userInput);
                validInput = true;
            } catch (NumberFormatException ex) {

            }
        }

        return userValue;

    }
    
    public static LocalDate readDate(String prompt){
        LocalDate ld = LocalDate.now();
        boolean validInput = false;
        
        while(!validInput){
            try {
            	ld = LocalDate.parse(readString(prompt), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                validInput = true;
            } catch(Exception ex){
                
            }

        }
        
        return ld;
    }

    public static int readInt(String prompt, int min, int max) {
        int userValue = readInt(prompt);

        while (userValue < min || userValue > max) {
           userValue = readInt(prompt);
        }
        
        return userValue;
    }
    
    public static BigDecimal readBigDecimal(String prompt){
        boolean validInput = false;
        BigDecimal userValue = BigDecimal.ZERO;
        
        while(!validInput){
            print(prompt);
            String userInput = scn.nextLine();
            
            try {
                userValue = new BigDecimal(userInput);
                validInput = true;
            } catch(Exception ex){
                
            }
        }
        
        return userValue;
    }
    
    public static BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max){
        BigDecimal userValue = readBigDecimal(prompt);

        while (userValue.compareTo(min) < 1 || userValue.compareTo(max) > 1) {
            userValue = readBigDecimal(prompt);
        }
        
        return userValue;
    }

    public static double readDouble(String prompt) {

        boolean validInput = false;
        double userValue = -1;

        while (!validInput) {
            print(prompt);
            String userInput = scn.nextLine();

            try {

                userValue = Double.parseDouble(userInput);
                validInput = true;
            } catch (NumberFormatException ex) {

            }
        }

        return userValue;
    }

    public static double readDouble(String prompt, double min, double max) {
        double userValue = readDouble(prompt);

        while (userValue < min || userValue > max) {
            userValue = readDouble(prompt);
        }
        
        return userValue;
    }
    
    public static float readFloat(String prompt) {

        boolean validInput = false;
        float userValue = -1;

        while (!validInput) {
            print(prompt);
            String userInput = scn.nextLine();

            try {

                userValue = Float.parseFloat(userInput);
                validInput = true;
            } catch (NumberFormatException ex) {

            }
        }

        return userValue;
    }

    public static float readFloat(String prompt, double min, double max) {
        float userValue = readFloat(prompt);

        while (userValue < min || userValue > max) {
            userValue = readFloat(prompt);
        }
        
        return userValue;
    }
    
    public static long readLong(String prompt) {

        boolean validInput = false;
        long userValue = -1;

        while (!validInput) {
            print(prompt);
            String userInput = scn.nextLine();

            try {

                userValue = Long.parseLong(userInput);
                validInput = true;
            } catch (NumberFormatException ex) {

            }
        }

        return userValue;
    }

    public static long readLong(String prompt, double min, double max) {
        long userValue = readLong(prompt);

        while (userValue < min || userValue > max) {
            userValue = readLong(prompt);
        }
        
        return userValue;
    }

    BigDecimal editBigDecimal(String prompt, BigDecimal originalValue) {
        String userInput = readString(prompt);
        BigDecimal toReturn = null;
        
        if (userInput.isEmpty()) {
            toReturn = originalValue;
        } else {
            toReturn = new BigDecimal(userInput);
        }

        return toReturn;
    }
}
