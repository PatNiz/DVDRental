package app;

import exception.*;
import io.files.ConsolePrinter;
import io.files.DataReader;
import io.files.FileManager;
import io.files.FileManagerBuilder;
import model.*;

import java.util.Comparator;
import java.util.InputMismatchException;

public class RentalControl {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;

    private Rental rental;

    RentalControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            rental = fileManager.importData();
            printer.printLine("IMPORTED DATE FROM FILE");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("INITIATED NEW DATA BASE");
            rental = new Rental();
        }
    }

    void controlLoop() {
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case EXIT -> exit();
                case USER -> goToUserPanel();
                case  ADMIN-> goToAdminPanel();
                default -> printer.printLine("THERE IS NO SUCH OPTION,PLEASE RE-ENTER: ");
            }
        } while (option != Option.EXIT);

    }
    void goToUserPanel() {
        UserOption userOption;
        do {
            printUserOptions();
            userOption = getUserOption();
            switch (userOption) {
                case RENT_DVD -> rentDVD();
                case PRINT_AVAIABLE_DVDS -> printAvaiableDVDs();
                case RETURN_DVD -> returnDVD();
                case PRINT_RENTED_DVDS ->printRentedDVDs();
                case FIND_DVD -> findDVD();
                case BACK -> controlLoop();
                default -> printer.printLine("THERE IS NO SUCH OPTION,PLEASE RE-ENTER: ");
            }
        } while (userOption != UserOption.BACK);

    }
    void goToAdminPanel(){
        AdminOption adminOption;
        do {
            printAdminOptions();
            adminOption = getAdminOption();
            switch (adminOption) {
                case ADD_DVD -> addDVD();
                case PRINT_DVDS -> printDVDs();
                case PRINT_AVAIABLE_DVDS ->printAvaiableDVDs();
                case DELETE_DVD -> deleteDVD();
                case ADD_USER -> addUser();
                case PRINT_USERS -> printUsers();
                case PRINT_RENTAL_DETAILS -> printRentalDetails();
                case FIND_DVD -> findDVD();
                case BACK -> controlLoop();
                default -> printer.printLine("THERE IS NO SUCH OPTION,PLEASE RE-ENTER: ");
            }
        } while (adminOption != AdminOption.BACK);
    }



    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage() + ", RE-ENTER:");
            } catch (InputMismatchException ignored) {
                printer.printLine("ENTERED VALUE IS NOT A NUMBER, RE-ENTER: ");
            }
        }

        return option;
    }
    private UserOption getUserOption() {
        boolean optionOk = false;
        UserOption option = null;
        while (!optionOk) {
            try {
                option = UserOption.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage() + ", RE-ENTER:");
            } catch (InputMismatchException ignored) {
                printer.printLine("ENTERED VALUE IS NOT A NUMBER, RE-ENTER: ");
            }
        }

        return option;
    }
    private AdminOption getAdminOption() {
        boolean optionOk = false;
        AdminOption option = null;
        while (!optionOk) {
            try {
                option = AdminOption.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage() + ", RE-ENTER:");
            } catch (InputMismatchException ignored) {
                printer.printLine("ENTERED VALUE IS NOT A NUMBER, RE-ENTER: ");
            }
        }

        return option;
    }

    private void printOptions() {
        printer.printLine("CHOOSE OPTION: ");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }
    private void printUserOptions() {
        printer.printLine("CHOOSE OPTION: ");
        for (UserOption option : UserOption.values()) {
            printer.printLine(option.toString());
        }
    }
    private void printAdminOptions() {
        printer.printLine("CHOOSE OPTION: ");
        for (AdminOption option : AdminOption.values()) {
            printer.printLine(option.toString());
        }
    }


    private void addDVD() {
        try {
            DVD dvd = dataReader.readAndCreateDVD();
            rental.addDVD(dvd);
            fileManager.exportData(rental);

        } catch (InputMismatchException e) {
            printer.printLine("FAILED TO CREATE DVD,INCORRECT DATA");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("CAPACITY LIMIT REACHED,CANNOT ADD ANOTHER DVD");
        }
    }
    private void rentDVD() {
            RentalDetails rentalDetails = dataReader.createRent();
            rental.rentDVD(rentalDetails);
            fileManager.exportData(rental);

    }

    private void returnDVD(){};

    private void addUser() {
        RentalUser rentalUser = dataReader.createRentalUser();
        try {
            rental.addUser(rentalUser);
            fileManager.exportData(rental);

        } catch (UserAlreadyExistsException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void printDVDs() {
        printer.printDVDs(rental.getSortedDVDs(
                Comparator.comparing(DVD::getTitle, String.CASE_INSENSITIVE_ORDER))
        );
    }
    private void printAvaiableDVDs(){
        printer.printDVDs(rental.getSortedAvaiableDVDs(
                Comparator.comparing(DVD::getTitle, String.CASE_INSENSITIVE_ORDER))
        );
    }
    private void printRentalDetails() {
        printer.printRentalDetails(rental.getSortedRentedDetails(
                Comparator.comparing(RentalDetails::getFromDate, String.CASE_INSENSITIVE_ORDER))
        );
    }

    private void printRentedDVDs() {
        printer.printLine("Your id:");
        int userId = dataReader.getInt();
        printer.printDVDs(rental.getSortedRentedDVDs(
                Comparator.comparing(DVD::getTitle, String.CASE_INSENSITIVE_ORDER),userId)
        );
    }

    private void printUsers() {
        printer.printUsers(rental.getSortedUsers(
                Comparator.comparing(User::getLastName, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void findDVD() {
        printer.printLine("ENTER DVD ID:");

        int dvdId = dataReader.getInt();
        String notFoundMessage = "NO DVD WITH THIS ID";
        rental.findDVDBydvdId(dvdId)
                .map(DVD::toString)
                .ifPresentOrElse(System.out::println, () -> System.out.println(notFoundMessage));
    }



    private void deleteDVD() {
        try {
            DVD dvd = dataReader.readAndCreateDVD();
            if (rental.removeDVD(dvd))
                printer.printLine("DVD DELETED.");
            else
                printer.printLine("THERE IS NOT SUCH A DVD.");
        } catch (InputMismatchException e) {
            printer.printLine("FAILED TO CREATE DVD,INCORRECT DATA");
        }
    }

    private void exit() {
        try {
            fileManager.exportData(rental);

            printer.printLine("SUCCESSFULLY EXPORT DATA TO FILE");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        dataReader.close();
        printer.printLine("BYE!");
        System.exit(0);
    }


    private enum Option {
        EXIT(0, "EXIT"),
        USER(1, "USER PANEL"),
        ADMIN(2, "ADMIN PANEL");

        private int value;
        private String description;

        Option(int value, String desc) {
            this.value = value;
            this.description = desc;
        }
        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("NO SUCH OPTION WITH THIS ID" + option);
            }
        }
    }
    private enum AdminOption {

        ADD_DVD(0, "ADD DVD"),
        PRINT_DVDS(1, "SHOW LIST DVD"),
        PRINT_AVAIABLE_DVDS(2,"SHOW AVAILABLE DVD"),
        DELETE_DVD(3, "DELETE DVD"),
        ADD_USER(4, "ADD RENTAL USER"),
        PRINT_USERS(5, "SHOW RENTAL USERS"),
        PRINT_RENTAL_DETAILS(6,"SHOW RENTAL DETAILS"),
        FIND_DVD(7, "FIND DVD BY ID"),
        BACK(8, "BACK");

        private int value;
        private String description;

        AdminOption(int value, String desc) {
            this.value = value;
            this.description = desc;
        }
        @Override
        public String toString() {
            return value + " - " + description;
        }

        static AdminOption createFromInt(int option) throws NoSuchOptionException {
            try {
                return AdminOption.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("NO SUCH OPTION WITH THIS ID" + option);
            }
        }
    }

    private enum UserOption {

        RENT_DVD(0, "Rent DVD"),
        PRINT_AVAIABLE_DVDS(1,"SHOW AVAIABLE DVD"),
        RETURN_DVD(2, "RETURN DVD"),
        PRINT_RENTED_DVDS(3,"PRINT RENTED DVDS"),
        FIND_DVD(4, "FIND DVD BY ID"),
        BACK(5, "BACK");

        private int value;
        private String description;

        UserOption(int value, String desc) {
            this.value = value;
            this.description = desc;
        }
        @Override
        public String toString() {
            return value + " - " + description;
        }

        static UserOption createFromInt(int option) throws NoSuchOptionException {
            try {
                return UserOption.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("NO SUCH OPTION WITH THIS ID" + option);
            }
        }
    }









}
