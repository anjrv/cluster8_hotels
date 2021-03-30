import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import hotels.*;

/**
 * Demo command line interface that shows basic functionality of the hotel
 * business logic layer
 * 
 * @author: Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 *          Steinn Stefánsson Thors
 */
public class Demo {
    private static final Scanner s = new Scanner(System.in);
    private static final Logic l = new Logic();

    public static void hotels() {

    }

    public static void rooms() {

    }

    public static void reservations() {

    }

    public static void reviews() {

    }

    public static void update() {
        boolean cont = true;
        System.out.println();
        System.out.println("What would you like to update?");
        System.out.println("1) Reservation");
        System.out.println("2) Review");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next();

            switch (response) {
            case "1":
                hotels();
            case "2":
                rooms();
            case "return":
                cont = false;
                break;
            default:
                System.out.println("Invalid input");
                break;
            }
        }
        return;
    }

    public static void query() {
        boolean cont = true;
        System.out.println();
        System.out.println("What would you like to query?");
        System.out.println("1) Hotels");
        System.out.println("2) Rooms");
        System.out.println("3) Reservations");
        System.out.println("4) Reviews");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next();

            switch (response) {
            case "1":
                hotels();
            case "2":
                rooms();
            case "3":
                reservations();
            case "4":
                reviews();
            case "return":
                cont = false;
                break;
            default:
                System.out.println("Invalid input");
                break;
            }
        }
        return;
    }

    public static void intro() {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("1) Query");
        System.out.println("2) Update");
        System.out.println("Type quit to stop.");

        while (s.hasNext()) {
            String response = s.next();

            switch (response) {
            case "1":
                query();
                break;
            case "2":
                update();
                break;
            case "quit":
                System.exit(0);
            default:
                System.out.println("Invalid input");
                break;
            }

            System.out.println();
            System.out.println("What would you like to do?");
            System.out.println("1) Query");
            System.out.println("2) Update");
            System.out.println("Type quit to stop.");
        }
    }

    public static void main(String[] args) {
        intro();
    }
}
