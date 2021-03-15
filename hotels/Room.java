package hotels;

import java.util.ArrayList;

/**
 * Object to represent a Room from the database.
 * 
 * @author: Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 *          Steinn Stefánsson Thors
 */
public class Room implements Comparable<Room> {
    private int rnumber;
    private String hname;
    private int price;
    private int beds;
    private int adults;
    private int children;
    private boolean wifi;
    private boolean breakfast;
    private ArrayList<Reservation> reservations;

    /**
     * Constructor method for a room object.
     * 
     * @param rn   The room number of the room
     * @param hn   The name of the hotel the room is in
     * @param pr   The per night cost (in ISK) of the room
     * @param be   The amount of beds in the room
     * @param ad   The number of adult beds in the room
     * @param ch   The number of child beds in the room
     * @param wifi The wifi indicator of the room
     * @param br   The breakfast indicator of the room
     * @param res  A list of reservations currently present for the room
     */
    public Room(int rn, String hn, int pr, int be, int ad, int ch, boolean wifi, boolean br,
            ArrayList<Reservation> res) {
        this.rnumber = rn;
        this.hname = hn;
        this.price = pr;
        this.beds = be;
        this.adults = ad;
        this.children = ch;
        this.wifi = wifi;
        this.breakfast = br;
        this.reservations = res;
    }

    /**
     * @return an int representing the room number
     */
    public int getRnumber() {
        return rnumber;
    }

    /**
     * @return a String that represents which hotel the room is in
     */
    public String getHname() {
        return hname;
    }

    /**
     * @return an int representing the price (in ISK) per night of the room
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return an int representing the amount of beds in the room
     */
    public int getBeds() {
        return beds;
    }

    /**
     * @return an int representing the number of adult beds in the room
     */
    public int getAdults() {
        return adults;
    }

    /**
     * @return an int representing the number of child beds in the room
     */
    public int getChildren() {
        return children;
    }

    /**
     * @return a boolean that represents whether the room has WiFi access
     */
    public boolean getWifi() {
        return wifi;
    }

    /**
     * @return a boolean that represents whether the room has breakfast service
     */
    public boolean getBreakfast() {
        return breakfast;
    }

    /**
     * @return a list of reservations tied to the room
     */
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public int compareTo(Room r) {
        return this.getPrice() > r.getPrice() ? 1 : r.getPrice() > this.getPrice() ? -1 : 0;
    }
}