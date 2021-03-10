import java.util.ArrayList;

public class Hotel {
    private String name;
    private String address;
    private String image;
    private int region;
    private boolean accessibility;
    private boolean gym;
    private boolean spa;
    private Room[] rooms;

    public Hotel(String n, String a, String img, int r, boolean acc, boolean gym, boolean spa, Room[] rms) {
        this.name = n;
        this.address = a;
        this.image = img;
        this.region = r;
        this.accessibility = acc;
        this.gym = gym;
        this.spa = spa;
        this.rooms = rms;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public int getRegion() {
        return region;
    }

    public boolean getAccess() {
        return accessibility;
    }

    public boolean getYolked() {
        return gym;
    }

    public boolean getSpa() {
        return spa;
    }

    public Room[] getRooms() {
        return rooms;
    }
}