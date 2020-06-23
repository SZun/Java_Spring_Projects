
import java.util.Random;
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
public class DogGenetics {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        System.out.print("What is your dog's name? ");

        String dogName = scn.nextLine();

        System.out.println("Well then, I have this highly reliable report on " + dogName + "'s prestigious background right here.");
        System.out.println("");
        System.out.println(dogName + " is:");
        System.out.println("");

        String[] dogBreeds = {"St. Bernard", "Chihuahua", "Dramatic RedNosed Asian Pug", "Common Cur", "King Doberman"};
        Random rng = new Random();

        int sum = 0;

        for (int i = 0; i < dogBreeds.length; i++) {
            
            int percentage = rng.nextInt(101 - sum);
            
            if (i == dogBreeds.length - 1) {
                percentage = 100 - sum;
            }
            
            sum += percentage;
            
            System.out.println(percentage + "% " + dogBreeds[i]);
        }

    }
}
