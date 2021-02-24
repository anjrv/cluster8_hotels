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
    private Review review;

    public Reservation() {
        // startDate
        // endDate
        // contact
        // hname
        // rnumber
    }

    public Reservation(boolean p) {
        // startDate
        // endDate
        // contact
        // hname
        // rnumber
        // paid
    }

    private void initialize() {
        // reservationID
        // creationDate
    }

    public String getResID() {
        return reservationID;
    }

    public long getCreate() {
        return creationDate;
    }

    public long getStart() {
        return startDate;
    }

    public long getEnd() {
        return endDate;
    }

    public boolean getCancel() {
        return cancelled;
    }

    public boolean getPaid() {
        return paid;
    }

    public String getContact() {
        return contact;
    }

    public String getHname() {
        return hname;
    }

    public int getRnumber() {
        return rnumber;
    }

    public Review getReview() {
        return review;
    }

    public void setCancel(boolean c) {
        this.cancelled = c;
    }

    public void setPaid(boolean p) {
        this.paid = p;
    }

    public void setReview(Review r) {
        this.review = r;
    }
}