/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.healthyhearts;

import java.util.Scanner;

/**
 *
 * @author samg.zun
 */
public class HealthyHearts {

    public static void main(String[] args) {

        int age, heartRate;
        Scanner myScanner = new Scanner(System.in);

        System.out.print("What is your age? ");
        age = myScanner.nextInt();
        heartRate = 220 - age;

        System.out.println("Your maximum heart rate should be " + heartRate + " beats per minute");
        System.out.println("Your target HR Zone is " + heartRate * .5 + "-" + heartRate * .85 + " beats per minute");

    }
}
