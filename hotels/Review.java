package hotels;

/**
 * Object to represent a Review from the database.
 * 
 * Authors: Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 * Steinn Stefánsson Thors
 */
public class Review {
    private final int grade;
    private final int rnumber;
    private final String hname;
    private final String text;
    private final String reservationID;

    public Review(int g, int rn, String hn, String t, String rID) {
        this.grade = g;
        this.rnumber = rn;
        this.hname = hn;
        this.text = t;
        this.reservationID = rID;
    }

    public int getGrade() {
        return grade;
    }

    public String getText() {
        return text;
    }
}