package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter

public class RentalDetails implements CsvConvertible{
    private int rentalId;
    private int userId;
    private int dvdId;
    private String fromDate;
    private String toDate;


    public RentalDetails(int rentalId, int userId, int dvdId, String fromDate, String toDate) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.dvdId = dvdId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    public RentalDetails(int userId, int dvdId, String fromDate, String toDate) {
        this.userId = userId;
        this.dvdId = dvdId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "RentalDetails{" +
                "rentalId=" + rentalId +
                ", userId=" + userId +
                ", dvdId=" + dvdId +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }

    @Override
    public String toCsv() {
        return
                getRentalId()   + ";" +
                getUserId()     + ";" +
                getDvdId()      + ";" +
                getFromDate()   + ";" +
                getToDate()     + ";" +
                "";
    }

}
