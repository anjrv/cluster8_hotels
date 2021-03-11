import java.util.ArrayList;
import hotels.Logic;
import hotels.Hotel;

public class Demo {
    public static void main(String[] args) {
        Logic bn = new Logic();
        String[] params = { "filler" };
        try {
            ArrayList<Hotel> hotels = bn.getHotels(params);
            for (int i = 0; i < hotels.size(); i++) {
                System.out.println(hotels.get(i).getName());
            }
        } catch (Exception e) {
        }
    }
}
