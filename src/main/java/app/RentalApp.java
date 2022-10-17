package app;

public class RentalApp {
    private static final String APP_NAME = "Rental 1.0";

    public static void main(String[] args) {
        System.out.println(APP_NAME);
        RentalControl rentalControl = new RentalControl();
        rentalControl.controlLoop();
    }
}
