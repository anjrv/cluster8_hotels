package hotels;

/**
 * Object to represent a Review from the database.
 * 
 * @author: Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 *          Steinn Stefánsson Thors
 */
public class Review implements Comparable<Review> {
    private final int grade;
    private final String hname;
    private final String text;

    /**
     * Constructor method for a review object.
     * 
     * @param g  an int that represents the grade of the review
     * @param hn a String that represents the hotel the review was given to
     * @param t  a String that represents the text body of the review
     */
    public Review(int g, String hn, String t) {
        this.grade = g;
        this.hname = hn;
        this.text = t;
    }

    /**
     * @return an int that represents the grade of the review
     */
    public int getGrade() {
        return grade;
    }

    /**
     * @return a String that represents the hotel the review was given to
     */
    public String getHname() {
        return hname;
    }

    /**
     * @return a String that represents the text body of the review
     */
    public String getText() {
        return text;
    }

    public int compareTo(Review r) {
        return this.getGrade() > r.getGrade() ? 1 : r.getGrade() > this.getGrade() ? -1 : 0;
    }
}