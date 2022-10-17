package model;

import javax.xml.bind.JAXB;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentalUser extends User{

    private Map<Integer, RentalDetails> rentedDVDs = new HashMap<>();

    public RentalUser(String firstName, String lastName, String birthDate, int userId) {
        super(firstName, lastName, birthDate, userId);
    }
    public RentalUser(String firstName, String lastName, String birthDate) {
        super(firstName, lastName, birthDate);
    }

    @Override
    public String toCsv() { return  getFirstName() + ";" +
                                    getLastName()  + ";" +
                                    getBirthDate() + ";" +
                                    getUserId();}



    @Override
    public String toXML(RentalUser user) {
        StringWriter sw = new StringWriter();
        JAXB.marshal(user, sw);
        String xmlString = sw.toString();
        System.out.println(xmlString);
        return xmlString;
    }
    public void addToRentedItems(RentalDetails details){
        int rentedDvdId= rentedDVDs.size()+1;
        rentedDVDs.put(rentedDvdId,details);
    }
}
