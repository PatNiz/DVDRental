package io.files;

import model.DVD;
import model.RentalDetails;
import model.RentalUser;
import model.User;

import java.util.Collection;

public class ConsolePrinter {
    public void printDVDs(Collection<DVD> DVDs) {
        DVDs.stream()
                .map(DVD::toString)
                .forEach(this::printLine);

    }
    public void printRentalDetails(Collection<RentalDetails> details) {
        details.stream()
                .map(RentalDetails::toString)
                .forEach(this::printLine);

    }




    public void printUsers(Collection<RentalUser> users) {
        users.stream()
                .map(User::toString)
                .forEach(this::printLine);
    }

    public void printLine(String text) {
        System.out.println(text);
    }
}
