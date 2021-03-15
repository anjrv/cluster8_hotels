package hotels;

import java.util.ArrayList;

/**
 * Object to represent a Hotel from the database.
 * 
 * @author: Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 *          Steinn Stefánsson Thors
 */
public class Hotel implements Comparable<Hotel> {
    private String name;
    private String address;
    private String image;
    private int region;
    private boolean accessibility;
    private boolean gym;
    private boolean spa;
    private ArrayList<Room> rooms;

    /**
     * Constructor method for a hotel object.
     * 
     * @param n   The name of the hotel
     * @param a   The address of the hotel
     * @param img The image path for this hotel
     * @param r   The region code of the hotel
     * @param acc The accessibility indicator of the hotel
     * @param gym The gym indicator of the hotel
     * @param spa The spa indicator of the hotel
     * @param rms A list of rooms present in the hotel
     */
    public Hotel(String n, String a, String img, int r, boolean acc, boolean gym, boolean spa, ArrayList<Room> rms) {
        this.name = n;
        this.address = a;
        this.image = img;
        this.region = r;
        this.accessibility = acc;
        this.gym = gym;
        this.spa = spa;
        this.rooms = rms;
    }

    /**
     * @return a String representing the name of the hotel
     */
    public String getName() {
        return name;
    }

    /**
     * @return a String representing the address of the hotel
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return a String that represents the file name of the image that represents
     *         the hotel from the hotel/images directory
     */
    public String getImage() {
        return image;
    }

    /**
     * @return an int representing the region of the hotel
     */
    public int getRegion() {
        return region;
    }

    /**
     * @return a boolean representing whether the hotel provides advanced
     *         accessibility
     */
    public boolean getAccess() {
        return accessibility;
    }

    /**
     * @return a boolean representing whether the hotel provides a gym
     */
    public boolean getGym() {
        return gym;
    }

    /**
     * @return a boolean representing whether the hotel provides a spa
     */
    public boolean getSpa() {
        return spa;
    }

    /**
     * @return an ArrayList of room objects representing the available rooms the
     *         hotel provides
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public int compareTo(Hotel h) {
        return this.getRooms().size() > h.getRooms().size() ? 1 : h.getRooms().size() > this.getRooms().size() ? -1 : 0;
    }
}