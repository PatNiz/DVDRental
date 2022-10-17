package io.files;


import model.Rental;

public interface FileManager {
    Rental importData();
    void exportData(Rental rental);

}
