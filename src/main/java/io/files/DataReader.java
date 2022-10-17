package io.files;

import model.DVD;
import model.RentalDetails;
import model.RentalUser;

import java.util.Scanner;

public class DataReader {
    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public void close() {
        sc.close();
    }

    public int getInt() {
        try {
            return sc.nextInt();
        } finally {
            sc.nextLine();
        }
    }

    public String getString() {
        return sc.nextLine();
    }
    public Integer getLong() { return sc.nextInt();}

    public DVD readAndCreateDVD() {
        printer.printLine("Title: ");
        String title = sc.nextLine();
        printer.printLine("Genre: ");
        String genre = sc.nextLine();
        printer.printLine("Year: ");
        int releaseDate = getInt();
        printer.printLine("Price: ");
        Double price = sc.nextDouble();
        printer.printLine("isAvailable: ");
        Boolean isAvailable = sc.nextBoolean();
        int userId = 0;
        return new DVD(title, genre, releaseDate, price, isAvailable, userId);
    }


    public RentalUser createRentalUser() {
        printer.printLine("Imię");
        String firstName = sc.nextLine();
        printer.printLine("Nazwisko");
        String lastName = sc.nextLine();
        printer.printLine("Birth Date");
        String birthDate = sc.nextLine();
        return new RentalUser(firstName,lastName, birthDate);
    }
    public RentalDetails createRent(){
        printer.printLine("UserId");
        int userId = Integer.valueOf(sc.nextLine());
        printer.printLine("dvdId");
        int dvdId = Integer.valueOf(sc.nextLine());
        printer.printLine("fromDate: ");
        String fromDate = sc.nextLine();
        printer.printLine("toDate: ");
        String toDate = sc.nextLine();
        return new RentalDetails(userId,dvdId,fromDate,toDate);
    }

}