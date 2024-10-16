package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter a number: ");
        String number = s.nextLine();
        System.out.println("Your number is: " + number);
    }
}