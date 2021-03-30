import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Date;

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

    private static void roomInfo(Room r) {
        System.out.println("------------------------------");
        System.out.println("Room number: " + r.getRnumber());
        System.out.println("Price: " + r.getPrice() + " ISK");
        System.out.println("Beds: " + r.getBeds());
        System.out.println("Adults: " + r.getAdults());
        System.out.println("Children: " + r.getChildren());
        System.out.println("WiFi: " + (r.getWifi() ? "YES" : "NO"));
        System.out.println("Breakfast: " + (r.getBreakfast() ? "YES" : "NO"));
    }

    private static void hotelInfo(Hotel h) {
        ArrayList<Room> rooms = h.getRooms();

        System.out.println("Additional information for: " + h.getName());
        System.out.println("Region: " + h.getRegion());
        System.out.println("Address: " + h.getAddress());
        System.out.println("Accessibility: " + (h.getAccess() ? "YES" : "NO"));
        System.out.println("Gym: " + (h.getGym() ? "YES" : "NO"));
        System.out.println("Spa: " + (h.getSpa() ? "YES" : "NO"));
        System.out.println("Available rooms:");
        for (int i = 0; i < rooms.size(); i++) {
            roomInfo(rooms.get(i));
        }
    }

    private static void showHotels(Hashtable<String, String> params, Hashtable<String, String> roomParams, long st,
            long e) {
        boolean cont = true;

        System.out.println();
        System.out.println("Type return to go back.");
        System.out.println("For further information about a hotel, type the number");

        ArrayList<Hotel> hotels = l.getHotels(params, roomParams, st, e);
        for (int i = 0; i < hotels.size(); i++) {
            System.out.println(i + ") " + hotels.get(i).getName());
        }

        while (cont && s.hasNext()) {
            String response = s.next();

            if (response.equals("return")) {
                cont = false;
                break;
            }
            hotelInfo(hotels.get(Integer.parseInt(response)));
        }
        return;
    }

    private static void timeConstraints(Hashtable<String, String> params, Hashtable<String, String> roomParams) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        long st = new Date().getTime();
        long e = new Date().getTime() + 31536000000L;

        boolean cont = true;
        System.out.println();
        System.out.println("Would you like to add a start and end date?");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next();
            if (response.equals("confirm")) {
                showHotels(params, roomParams, st, e);
            } else if (response.equals("return")) {
                cont = false;
                break;
            } else {
                String[] paramStrings = response.split(",");
                try {
                    st = sdf.parse(paramStrings[0]).getTime();
                    e = sdf.parse(paramStrings[1]).getTime();
                } catch (Exception err) {
                    System.err.println(err);
                }
            }
        }
        return;

    }

    private static void roomParams(Hashtable<String, String> params) {
        Hashtable<String, String> roomParams = new Hashtable<String, String>();

        boolean cont = true;
        System.out.println();
        System.out.println("Would you like to add room parameters?");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next();
            if (response.equals("confirm")) {
                timeConstraints(params, roomParams);
            } else if (response.equals("return")) {
                cont = false;
                break;
            } else {
                String[] paramStrings = response.split(",");
                roomParams.put(paramStrings[0], paramStrings[1]);
            }
        }
        return;
    }

    private static void hotels() {
        Hashtable<String, String> params = new Hashtable<String, String>();

        boolean cont = true;
        System.out.println();
        System.out.println("What hotel paramaters would you like to use?");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next();
            if (response.equals("confirm")) {
                roomParams(params);
            } else if (response.equals("return")) {
                cont = false;
                break;
            } else {
                String[] paramStrings = response.split(",");
                params.put(paramStrings[0], paramStrings[1]);
            }
        }
        return;
    }

    private static void reservations() {
        Hashtable<String, String> params = new Hashtable<String, String>();

    }

    private static void reviews() {
        Hashtable<String, String> params = new Hashtable<String, String>();

    }

    private static void query() {
        boolean cont = true;
        System.out.println();
        System.out.println("What would you like to query?");
        System.out.println("1) Hotels");
        System.out.println("2) Reservations");
        System.out.println("3) Reviews");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next();

            switch (response) {
            case "1":
                hotels();
            case "2":
                reservations();
            case "3":
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

    private static void reservationTimeConstraints(Hashtable<String, String> params) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        long st = new Date().getTime();
        long e = new Date().getTime() + 31536000000L;

        boolean cont = true;
        System.out.println();
        System.out.println("Please add a start and end date.");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next();
            if (response.equals("confirm")) {
                String resID = l.setReservation(params, st, e);
                System.out.println("Success! Your reservation ID is: " + resID);
            } else if (response.equals("return")) {
                cont = false;
                break;
            } else {
                String[] paramStrings = response.split(",");
                try {
                    st = sdf.parse(paramStrings[0]).getTime();
                    e = sdf.parse(paramStrings[1]).getTime();
                } catch (Exception err) {
                    System.err.println(err);
                }
            }
        }
        return;

    }

    private static void addReservation() {
        Hashtable<String, String> params = new Hashtable<String, String>();

        boolean cont = true;
        System.out.println();
        System.out.println("Please enter the details of your desired reservation.");
        System.out.println("Required fields are: Hotel name, Room number, Contact email");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next();
            if (response.equals("confirm")) {
                reservationTimeConstraints(params);
            } else if (response.equals("return")) {
                cont = false;
                break;
            } else {
                String[] paramStrings = response.split(",");
                params.put(paramStrings[0], paramStrings[1]);
            }
        }
        return;
    }

    private static void changeReservation() {

    }

    private static void addReview() {

    }

    private static void update() {
        boolean cont = true;
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("1) Add a reservation");
        System.out.println("2) Update a reservation");
        System.out.println("3) Add a review");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next();

            switch (response) {
            case "1":
                addReservation();
            case "2":
                changeReservation();
            case "3":
                addReview();
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

    private static void intro() {
        s.useDelimiter("(\\n)");

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
