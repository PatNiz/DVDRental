package io.files;

import exception.DataExportException;
import exception.DataImportException;
import model.*;

import java.io.*;
import java.util.Collection;

public class CsvFileManager implements FileManager {
    private static final String DVDS_FILE_NAME = "dvd.csv";
    private static final String USERS_FILE_NAME = "Rental_users.csv";
    private static final String RENTAL_DETAILS_FILE_NAME = "Rental_details.csv";

    @Override
    public void exportData(Rental rental) {
        exportDVDs(rental);
        exportRentalDetails(rental);
        exportUsers(rental);
    }

    @Override
    public Rental importData() {
        Rental rental = new Rental();
        importDVDs(rental);
        importUsers(rental);
        importRentalDetails(rental);
        return rental;
    }

    private void exportDVDs(Rental rental) {
        Collection<DVD> DVDs = rental.getDVDs().values();
        exportToCsv(DVDs, DVDS_FILE_NAME);
    }
    private void exportRentalDetails(Rental rental){
        Collection<RentalDetails> details = rental.getRentedDetails().values();
        exportToCsv(details,RENTAL_DETAILS_FILE_NAME);
    }
    private void exportUsers(Rental rental) {
        Collection<RentalUser> users = rental.getUsers().values();
        exportToCsv(users, USERS_FILE_NAME);
    }


    private <T extends CsvConvertible> void exportToCsv(Collection<T> collection, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (T element : collection) {
                bufferedWriter.write(element.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku " + fileName);
        }
    }

    private void importDVDs(Rental rental) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(DVDS_FILE_NAME))) {
            bufferedReader.lines()
                    .map(this::createDVDFromString)
                    .forEach(rental::addDVD);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + DVDS_FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Błąd odczytu pliku " + DVDS_FILE_NAME);
        }
    }
    private void importUsers(Rental rental) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_FILE_NAME))) {
            bufferedReader.lines()
                    .map(this::createUserFromString)
                    .forEach(rental::addUser);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + USERS_FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Błąd odczytu pliku " + USERS_FILE_NAME);
        }

    }
    private void importRentalDetails(Rental rental) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(RENTAL_DETAILS_FILE_NAME))) {
            bufferedReader.lines()
                    .map(this::createRentalDetailFromString)
                    .forEach(rental::addRent);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + RENTAL_DETAILS_FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Błąd odczytu pliku " + RENTAL_DETAILS_FILE_NAME);
        }

    }



    private RentalDetails createRentalDetailFromString(String csvText) {
        String[] split = csvText.split(";");
        int rentalId=Integer.parseInt(split[0]);
        int userId=Integer.parseInt(split[1]);
        int dvdId=Integer.parseInt(split[2]);
        String fromDate=split[3];
        String toDate=split[4];
        return new RentalDetails(rentalId,userId,dvdId,fromDate,toDate);
    }
    private DVD createDVDFromString(String csvText) {
        String[] split = csvText.split(";");
        String title = split[0];
        String genre = split[1];
        int releaseDate = Integer.parseInt(split[2]);
        double price = Double.parseDouble(split[3]);
        boolean isAvailable = Boolean.parseBoolean(split[4]);
        int userId = Integer.parseInt(split[5]);
        int dvdId = Integer.parseInt(split[6]);
        return new DVD(title, genre, releaseDate, price, isAvailable,userId, dvdId);
    }
    private RentalUser createUserFromString(String csvText) {
        String[] split = csvText.split(";");
        String firstName = split[0];
        String lastName = split[1];
        String birthDate = split[2];
        int userId = Integer.valueOf(split[3]);
        return new RentalUser(firstName, lastName, birthDate, userId);
    }
}
