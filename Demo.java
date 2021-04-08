import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Date;

import hotels.*;

/**
 * Demo command line interface that shows basic functionality of the hotel
 * business logic layer.
 * 
 * @author: Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 *          Steinn Stefánsson Thors
 */
public class Demo {
    private static final Scanner s = new Scanner(System.in); // Scanner used for user input
    private static final Logic l = new Logic(); // Hotel business logic controller

    /**
     * Helper function that prints available parameter options to StdOut.
     * 
     * @param type String representing the parameters to get
     */
    private static void getParams(String type) {
        String[] params = null;

        switch (type) {
        case "hotels":
            params = l.getHotelParams();
            break;
        case "reservations":
            params = l.getReservationParams();
            break;
        case "reviewInsert":
            params = l.getReviewInsertParams();
            break;
        case "reviewSelect":
            params = l.getReviewSelectParams();
            break;
        case "rooms":
            params = l.getRoomParams();
            break;
        }

        System.out.println("------------------------------------------");
        for (int i = 0; i < params.length; i++) {
            System.out.println(params[i]);
        }
    }

    /**
     * Helper function to print information about a given room to StdOut.
     *
     * @param r the room to print
     */
    private static void roomInfo(Room r) {
        System.out.println("------------------------------------------");
        System.out.println("Room number: " + r.getRnumber());
        System.out.println("Price: " + r.getPrice() + " ISK");
        System.out.println("Beds: " + r.getBeds());
        System.out.println("Adults: " + r.getAdults());
        System.out.println("Children: " + r.getChildren());
        System.out.println("WiFi: " + (r.getWifi() ? "YES" : "NO"));
        System.out.println("Breakfast: " + (r.getBreakfast() ? "YES" : "NO"));
    }

    /**
     * Helper function to print information about a given hotel to StdOut.
     *
     * @param h the hotel to print
     */
    private static void hotelInfo(Hotel h) {
        ArrayList<Room> rooms = h.getRooms();

        System.out.println();
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

    /**
     * Fetches a list of Hotel objects and displays them in an accessible manner.
     * 
     * @param params     Hashtable of hotel parameters (can be empty)
     * @param roomParams Hashtable of room parameters (can be empty)
     * @param st         long representing start date
     * @param e          long representing end date
     */
    private static void showHotels(Hashtable<String, String> params, Hashtable<String, String> roomParams, long st,
            long e) {
        System.out.println();
        System.out.println("For further information about a hotel, type the number");
        System.out.println("Type return to go back.");

        ArrayList<Hotel> hotels = l.getHotels(params, roomParams, st, e);
        for (int i = 0; i < hotels.size(); i++) {
            System.out.println(i + ") " + hotels.get(i).getName());
        }

        while (s.hasNext()) {
            String response = s.next().toLowerCase();

            if (response.equals("return")) {
                break;
            }
            try {
                int index = Integer.parseInt(response);
                hotelInfo(hotels.get(index));
                continue;
            } catch (Exception err) {
                System.out.println("Invalid input");
            }

            System.out.println();
            System.out.println("For further information about a hotel, type the number");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Helper function to get start and end dates from user and convert them to ms.
     * 
     * @param params     Hashtable of hotel parameters (can be empty)
     * @param roomParams Hashtable of room parameters (can be empty)
     */
    private static void queryTimeConstraints(Hashtable<String, String> params, Hashtable<String, String> roomParams) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        long st = new Date().getTime();
        long e = new Date().getTime() + 31536000000L;

        System.out.println();
        System.out.println("Please add a start and end date.");
        System.out.println("Start date and end date should be written in the format DD-MM-YYYY,DD-MM-YYYY");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (s.hasNext()) {
            String response = s.next().toLowerCase();
            if (response.equals("confirm")) {
                showHotels(params, roomParams, st, e);
            } else if (response.equals("return")) {
                break;
            } else {
                String[] paramStrings = response.split(",");
                try {
                    st = sdf.parse(paramStrings[0]).getTime();
                    e = sdf.parse(paramStrings[1]).getTime();
                    continue;
                } catch (Exception err) {
                    System.out.println(
                            "Input must be two dates, separated by a comma and in the form DD-MM-YYYY,DD-MM-YYYY");
                }
            }

            System.out.println();
            System.out.println("Would you like to add a start and end date?");
            System.out.println("Start date and end date should be written in the format DD-MM-YYYY,DD-MM-YYYY");
            System.out.println("Type confirm to proceed.");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Helper function to get room parameters from user (if any).
     * 
     * @param params Hashtable of hotel parameters (can be empty)
     */
    private static void roomParams(Hashtable<String, String> params) {
        Hashtable<String, String> roomParams = new Hashtable<String, String>();

        System.out.println();
        System.out.println("Would you like to add room parameters?");
        System.out.println("Insert desired parameters as key value pairs separated by a comma.");
        System.out.println("Type help to see available parameters.");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (s.hasNext()) {
            String response = s.next().toLowerCase();
            if (response.equals("confirm")) {
                queryTimeConstraints(params, roomParams);
            } else if (response.equals("return")) {
                break;
            } else if (response.equals("help")) {
                getParams("rooms");
                continue;
            } else if (!response.contains(",")) {
                System.out.println("Input must be a key, value pair separated by a comma!");
            } else {
                String[] paramStrings = response.split(",");
                if (paramStrings.length == 2) {
                    roomParams.put(paramStrings[0], paramStrings[1]);
                    continue;
                }

                System.out.println("Input format is incorrect.");
            }

            System.out.println();
            System.out.println("Would you like to add room parameters?");
            System.out.println("Insert desired parameters as key value pairs separated by a comma.");
            System.out.println("Type help to see available parameters.");
            System.out.println("Type confirm to proceed.");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Helper function to get hotel parameters from user (if any).
     */
    private static void hotels() {
        Hashtable<String, String> params = new Hashtable<String, String>();

        System.out.println();
        System.out.println("What hotel paramaters would you like to use?");
        System.out.println("Insert desired parameters as key value pairs separated by a comma.");
        System.out.println("Type help to see available parameters.");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (s.hasNext()) {
            String response = s.next().toLowerCase();
            if (response.equals("confirm")) {
                roomParams(params);
            } else if (response.equals("return")) {
                break;
            } else if (response.equals("help")) {
                getParams("hotels");
                continue;
            } else if (!response.contains(",")) {
                System.out.println("Input must be a key, value pair separated by a comma!");
            } else {
                String[] paramStrings = response.split(",");
                if (paramStrings.length == 2) {
                    params.put(paramStrings[0], paramStrings[1]);
                    continue;
                }

                System.out.println("Input format is incorrect.");
            }

            System.out.println();
            System.out.println("What hotel paramaters would you like to use?");
            System.out.println("Insert desired parameters as key value pairs separated by a comma.");
            System.out.println("Type help to see available parameters.");
            System.out.println("Type confirm to proceed.");
            System.out.println("Type return to go back.");

        }
        return;
    }

    /**
     * Fetches a list of reservation objects and displays them in an accessible
     * manner.
     * 
     * @param params Hashtable of reservation parameters (can be empty)
     */
    private static void showReservations(Hashtable<String, String> params) {
        System.out.println();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        ArrayList<Reservation> reservations = l.getReservations(params);

        for (int i = 0; i < reservations.size(); i++) {
            System.out.println("------------------------------------------");
            System.out.println(
                    "Hotel and room: " + reservations.get(i).getHname() + " " + reservations.get(i).getRnumber());
            System.out.println("Start date: " + sdf.format(new Date(reservations.get(i).getStart())));
            System.out.println("End date: " + sdf.format(new Date(reservations.get(i).getEnd())));
        }
    }

    /**
     * Helper function to get reservation parameters from user (if any).
     */
    private static void reservations() {
        Hashtable<String, String> params = new Hashtable<String, String>();

        System.out.println();
        System.out.println("What reservation paramaters would you like to use?");
        System.out.println("Type help to see available parameters.");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (s.hasNext()) {
            String response = s.next().toLowerCase();
            if (response.equals("confirm")) {
                showReservations(params);
                continue;
            } else if (response.equals("help")) {
                getParams("reservations");
                continue;
            } else if (response.equals("return")) {
                break;
            } else if (!response.contains(",")) {
                System.out.println("Input must be a key, value pair separated by a comma!");
            } else {
                String[] paramStrings = response.split(",");
                if (paramStrings.length == 2) {
                    params.put(paramStrings[0], paramStrings[1]);
                    continue;
                }

                System.out.println("Input format is incorrect.");
            }

            System.out.println();
            System.out.println("What reservation paramaters would you like to use?");
            System.out.println("Type confirm to proceed.");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Helper function to print infromation about reviews to StdOut.
     * 
     * @param params the review parameters to use (if any)
     */
    private static void showReviews(Hashtable<String, String> params) {
        System.out.println();

        ArrayList<Review> reviews = l.getReviews(params);

        for (int i = 0; i < reviews.size(); i++) {
            System.out.println("------------------------------------------");
            System.out.println("Hotel: " + reviews.get(i).getHname());
            System.out.println("Score: " + reviews.get(i).getGrade());
            System.out.println("Review body: ");
            System.out.println(reviews.get(i).getText());
        }
    }

    /**
     * Helper function to get review parameters from user (if any).
     */
    private static void reviews() {
        Hashtable<String, String> params = new Hashtable<String, String>();

        System.out.println();
        System.out.println("What review paramaters would you like to use?");
        System.out.println("Type help to see available parameters.");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (s.hasNext()) {
            String response = s.next();
            if (response.toLowerCase().equals("confirm")) {
                showReviews(params);
                continue;
            } else if (response.toLowerCase().equals("help")) {
                getParams("reviewSelect");
                continue;
            } else if (response.toLowerCase().equals("return")) {
                break;
            } else if (!response.contains(",")) {
                System.out.println("Input must be a key, value pair separated by a comma!");
            } else {
                String[] paramStrings = response.split(",");
                if (paramStrings.length == 2) {
                    params.put(paramStrings[0], paramStrings[1]);
                    continue;
                }

                System.out.println("Input format is incorrect.");
            }

            System.out.println();
            System.out.println("What reservation paramaters would you like to use?");
            System.out.println("Type confirm to proceed.");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Provides query routing to the user.
     */
    private static void query() {
        boolean cont = true;

        System.out.println();
        System.out.println("What would you like to query?");
        System.out.println("1) Hotels");
        System.out.println("2) Reservations");
        System.out.println("3) Reviews");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next().toLowerCase();

            switch (response) {
            case "1":
                hotels();
                break;
            case "2":
                reservations();
                break;
            case "3":
                reviews();
                break;
            case "return":
                cont = false;
                break;
            default:
                System.out.println("Invalid input");
                break;
            }

            System.out.println();
            System.out.println("What would you like to query?");
            System.out.println("1) Hotels");
            System.out.println("2) Reservations");
            System.out.println("3) Reviews");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Gets the start date and end date for a reservation and inserts it if it is
     * valid.
     * 
     * @param params parameters to be used for the reservation
     */
    private static void reservationTimeConstraints(Hashtable<String, String> params) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        long st = new Date().getTime();
        long e = new Date().getTime() + 31536000000L;

        System.out.println();
        System.out.println("Please add a start and end date.");
        System.out.println("Start date and end date should be written in the format DD-MM-YYYY,DD-MM-YYYY");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (s.hasNext()) {
            String response = s.next().toLowerCase();
            if (response.equals("confirm")) {
                String resID = l.setReservation(params, st, e);
                System.out.println("Success! Your reservation ID is: " + resID);
                continue;
            } else if (response.equals("return")) {
                break;
            } else {
                String[] paramStrings = response.split(",");
                try {
                    st = sdf.parse(paramStrings[0]).getTime();
                    e = sdf.parse(paramStrings[1]).getTime();
                    continue;
                } catch (Exception err) {
                    System.out.println(
                            "Input must be two dates, separated by a comma and in the form DD-MM-YYYY,DD-MM-YYYY");
                }
            }

            System.out.println();
            System.out.println("Please add a start and end date.");
            System.out.println("Start date and end date should be written in the format DD-MM-YYYY,DD-MM-YYYY");
            System.out.println("Type confirm to proceed.");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Helper function to get reservation parameters from user. (cannot be empty)
     * Required parameters are: hname, rnumber, contact
     */
    private static void addReservation() {
        Hashtable<String, String> params = new Hashtable<String, String>();

        System.out.println();
        System.out.println("Please enter the details of your desired reservation.");
        System.out.println("Required fields are: Hotel name, Room number, Contact email");
        System.out.println("Type help to see the format of the parameters.");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (s.hasNext()) {
            String response = s.next();
            if (response.toLowerCase().equals("confirm")) {
                reservationTimeConstraints(params);
            } else if (response.toLowerCase().equals("return")) {
                break;
            } else if (response.toLowerCase().equals("help")) {
                getParams("reservations");
                continue;
            } else {
                String[] paramStrings = response.split(",");
                if (paramStrings.length == 2) {
                    params.put(paramStrings[0], paramStrings[1]);
                    continue;
                }

                System.out.println("Input format is incorrect.");
            }

            System.out.println();
            System.out.println("Please enter the details of your desired reservation.");
            System.out.println("Required fields are: Hotel name, Room number, Contact email");
            System.out.println("Type confirm to proceed.");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Gets a reservationID from the user and cancels that reservation if it exists.
     */
    private static void cancelReservation() {
        System.out.println();
        System.out.println("Please enter reservationID for the reservation you want to cancel.");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");
        String resID = null;

        while (s.hasNext()) {
            String response = s.next();
            if (response.toLowerCase().equals("confirm")) {
                if (resID != null) {
                    System.out.println(resID);
                    try {
                        l.cancelReservation(resID);
                    } catch (Exception e) {
                        System.out.println("Reservation ID is invalid.");
                    }
                    resID = null;
                }
            } else if (response.toLowerCase().equals("return")) {
                break;
            } else {
                resID = response;
                continue;
            }

            System.out.println();
            System.out.println("Please enter reservation ID for the reservation you want to cancel.");
            System.out.println("Type confirm to proceed.");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Gets a date from the user from StdIn in the format DD-MM-YYYY and returns a
     * long millisecond representation of it.
     * 
     * @return long the date in milliseconds
     */
    private static long getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        long date = 0L;

        System.out.println();
        System.out.println("Please enter the date you would like to change to.");
        System.out.println("The date should be written in the format DD-MM-YYYY");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (s.hasNext()) {
            String response = s.next().toLowerCase();
            if (response.equals("confirm") || response.equals("return")) {
                break;
            } else {
                try {
                    date = sdf.parse(response).getTime();
                    continue;
                } catch (Exception err) {
                    System.out.println("Input must be a date in the form DD-MM-YYYY");
                }
            }
        }
        return date;
    }

    /**
     * Updates the start or end of a reservation based on the reservationID from the user.
     *
     * @param which boolean toggle that represents whether we are updating the start
     *              or end of a reservation
     */
    private static void changeDate(boolean which) {
        System.out.println();
        System.out.println("Please enter reservation ID for the reservation you want to change.");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");
        String resID = null;
        long newDate = 0L;

        while (s.hasNext()) {
            String response = s.next();
            if (response.toLowerCase().equals("confirm")) {
                newDate = getDate();
                if (newDate != 0L) {
                    try {
                        if (which) {
                            l.updateReservationStart(resID, newDate);
                        } else {
                            l.updateReservationEnd(resID, newDate);
                        }
                        continue;
                    } catch (Exception err) {
                        System.out.println(err);
                    }
                } else {
                    System.out.println("Invalid date");
                }
            } else if (response.toLowerCase().equals("return")) {
                break;
            } else {
                resID = response;
                continue;
            }

            System.out.println();
            System.out.println("Please enter reservation ID for the reservation you want to change.");
            System.out.println("Type confirm to proceed.");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Reservation update routing.
     */
    private static void changeReservation() {
        boolean cont = true;
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("1) Cancel a reservation");
        System.out.println("2) Change start date");
        System.out.println("3) Change end date");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next().toLowerCase();

            switch (response) {
            case "1":
                cancelReservation();
                break;
            case "2":
                changeDate(true);
                break;
            case "3":
                changeDate(false);
                break;
            case "return":
                cont = false;
                break;
            default:
                System.out.println("Invalid input");
                break;
            }

            System.out.println();
            System.out.println("What would you like to do?");
            System.out.println("1) Cancel a reservation");
            System.out.println("2) Change start date");
            System.out.println("3) Change end date");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Adds a review to the database based on given parameters.
     * Required parameters are: grade, text, reservationID, hname
     * 
     * Review can only be given if user has an existing reservationID.
     */
    private static void addReview() {
        Hashtable<String, String> params = new Hashtable<String, String>();

        System.out.println();
        System.out.println("Please enter the details of your review.");
        System.out.println("Type help to see the required parameters.");
        System.out.println("Type confirm to proceed.");
        System.out.println("Type return to go back.");

        while (s.hasNext()) {
            String response = s.next();
            if (response.toLowerCase().equals("confirm")) {
                l.setReview(params);
                System.out.println("Your review has been added.");
                continue;
            } else if (response.toLowerCase().equals("return")) {
                break;
            } else if (response.toLowerCase().equals("help")) {
                getParams("reviewInsert");
                continue;
            } else {
                String[] paramStrings = response.split(",");
                if (paramStrings.length == 2) {
                    params.put(paramStrings[0], paramStrings[1]);
                    continue;
                }

                System.out.println("Input format is incorrect.");
            }

            System.out.println();
            System.out.println("Please enter the details of your review.");
            System.out.println("Type help to see the required parameters.");
            System.out.println("Type confirm to proceed.");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Database update routing.
     */
    private static void update() {
        boolean cont = true;
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("1) Add a reservation");
        System.out.println("2) Update a reservation");
        System.out.println("3) Add a review");
        System.out.println("Type return to go back.");

        while (cont && s.hasNext()) {
            String response = s.next().toLowerCase();

            switch (response) {
            case "1":
                addReservation();
                break;
            case "2":
                changeReservation();
                break;
            case "3":
                addReview();
                break;
            case "return":
                cont = false;
                break;
            default:
                System.out.println("Invalid input");
                break;
            }

            System.out.println();
            System.out.println("What would you like to do?");
            System.out.println("1) Add a reservation");
            System.out.println("2) Update a reservation");
            System.out.println("3) Add a review");
            System.out.println("Type return to go back.");
        }
        return;
    }

    /**
     * Main menu routing.
     */
    private static void intro() {
        s.useDelimiter(System.lineSeparator());

        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("1) Query");
        System.out.println("2) Update");
        System.out.println("Type quit to stop.");

        while (s.hasNext()) {
            String response = s.next().toLowerCase();

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

    // Show main menu route if program is called explicitly.
    public static void main(String[] args) {
        intro();
    }
}
