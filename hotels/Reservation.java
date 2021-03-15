package hotels;

/**
 * Object to represent a Reservation from the database.
 * 
 * @author: Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 *          Steinn Stefánsson Thors
 */
public class Reservation {
    private String reservationID;
    private long creationDate;
    private long startDate;
    private long endDate;
    private boolean cancelled;
    private boolean paid;
    private String contact;
    private String hname;
    private int rnumber;

    /**
     * Constructor method for a reservation object.
     * 
     * @param resID a String representing the reservation ID of this reservation
     * @param cd    a long representing the creation time of this reservation (in
     *              milliseconds)
     * @param sd    a long representing the start time of this reservation (in
     *              milliseconds)
     * @param ed    a long representing the end time of this reservation (in
     *              milliseconds)
     * @param c     a boolean that indicates whether the reservation has been
     *              cancelled
     * @param p     a boolean that indicates whether the reservation has been paid
     *              for
     * @param cont  a String representing the contact information of the client for
     *              this reservation
     * @param hn    a String representing the hotel name for this reservation
     * @param rn    an int representing the room number of this reservation
     */
    public Reservation(String resID, long cd, long sd, long ed, boolean c, boolean p, String cont, String hn, int rn) {
        this.reservationID = resID;
        this.creationDate = cd;
        this.startDate = sd;
        this.endDate = ed;
        this.cancelled = c;
        this.paid = p;
        this.contact = cont;
        this.hname = hn;
        this.rnumber = rn;
    }

    /**
     * @return a String representing the reservation ID of the reservation
     */
    public String getResID() {
        return reservationID;
    }

    /**
     * @return a long representing the creation time of this reservation (in
     *         milliseconds)
     */
    public long getCreate() {
        return creationDate;
    }

    /**
     * @return a long representing the start time of this reservation (in
     *         milliseconds)
     */
    public long getStart() {
        return startDate;
    }

    /**
     * @return a long representing the end time of this reservation (in
     *         milliseconds)
     */
    public long getEnd() {
        return endDate;
    }

    /**
     * @return a boolean that indicates whether the reservation has been cancelled
     */
    public boolean getCancel() {
        return cancelled;
    }

    /**
     * @return a boolean that indicates whether the reservation has been paid for
     */
    public boolean getPaid() {
        return paid;
    }

    /**
     * @return a String representing the contact information of the client for this
     *         reservation
     */
    public String getContact() {
        return contact;
    }

    /**
     * @return a String representing the hotel name for this reservation
     */
    public String getHname() {
        return hname;
    }

    /**
     * @return an int representing the room number of this reservation
     */
    public int getRnumber() {
        return rnumber;
    }
}