// import java.util.ArrayList;

public class Room {
    private int rnumber;
    private String hname;
    private int price;
    private int beds;
    private int adults;
    private int children;
    private boolean wifi;
    private boolean breakfast;
    private Reservation[] reservations; // Change to a list type

    public Room() {
        // ...
    }

    public int getRnumber() {
        return rnumber;
    }

    public String hname() {
        return hname;
    }

    public int getBeds() {
        return beds;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public boolean getWifi() {
        return wifi;
    }

    public boolean getBreakfast() {
        return breakfast;
    }

    public void addReservation(Reservation r) {
        // append
    }
}