
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author samg.zun
 */
public class ConsoleIO {

    //prints the message to the screen
    public static void print(String message) {
        System.out.print(message);
    }

    //prints the prompt to the user
    //then retrieves a string from the user and returns it
    public static String readString(String prompt) {
        print(prompt);

        Scanner scn = new Scanner(System.in);

        String userInput = scn.nextLine();

        return userInput;

    }

    public static int readInt(String prompt) {

        Scanner scn = new Scanner(System.in);

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

    public static int readInt(String prompt, int min, int max) {
        int userValue = readInt(prompt);

        while (userValue < min || userValue > max) {
           userValue = readInt(prompt);
        }
        
        return userValue;
    }

    public static double readDouble(String prompt) {
        Scanner scn = new Scanner(System.in);

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
        Scanner scn = new Scanner(System.in);

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
        Scanner scn = new Scanner(System.in);

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

}
