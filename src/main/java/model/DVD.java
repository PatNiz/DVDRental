package model;

import io.files.CsvFileManager;
import lombok.*;

import javax.xml.bind.JAXB;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

public  class DVD implements CsvConvertible {
    private Integer dvdId;
    private String title;
    private String genre;
    private int releaseDate;
    private Double price;
    private Boolean isAvailable;
    private int userId;



    public DVD(String title, String genre, Integer releaseDate, Double price, Boolean isAvailable,int userId, Integer dvdId) {
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.price = price;
        this.isAvailable = isAvailable;
        this.userId = userId;
        this.dvdId = dvdId;
    }
    public DVD(String title, String genre, Integer releaseDate, Double price, Boolean isAvailable,int userId) {
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.price = price;
        this.isAvailable = isAvailable;
        this.userId = userId;
    }
   /* public DVD(String title, String genre, Integer releaseDate, Double price, Boolean isAvailable, Long dvdId) {
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.price = price;
        this.isAvailable = isAvailable;
        this.dvdId = dvdId;
    }*/



    public String toCsv() {
        return

                getTitle()          + ";" +
                getGenre()          + ";" +
                getReleaseDate()    + ";" +
                getPrice()          + ";" +
                getIsAvailable()    + ";" +
                getUserId()         + ";" +
                getDvdId() + ";" +"";
    }


    public String toXML(DVD dvd) {
        StringWriter sw = new StringWriter();
        JAXB.marshal(dvd, sw);
        String xmlString = sw.toString();
        System.out.println(xmlString);
        return xmlString;
    }


    public Element toXml(Document doc,int i ) throws ParserConfigurationException {


            Element dvd = doc.createElement("dvd");
            Element title = doc.createElement("title");
            title.setTextContent(getTitle());
            Element genre = doc.createElement("genre");
            genre.setTextContent(getGenre());
            Element releaseDate = doc.createElement("releaseDate");
            releaseDate.setTextContent(String.valueOf(getReleaseDate()));
            Element price = doc.createElement("price");
            price.setTextContent(String.valueOf(getPrice()));
            Element isAvailable = doc.createElement("isAvailable");
            isAvailable.setTextContent(String.valueOf(getIsAvailable()));
            dvd.setAttribute("id", String.valueOf(i));
            dvd.appendChild(title);
            dvd.appendChild(genre);
            dvd.appendChild(releaseDate);
            dvd.appendChild(price);
            dvd.appendChild(isAvailable);

        return dvd;
    }




    @Override
    public String toString() {

        if (isAvailable != false) {
            return "DVD{" +
                    "dvdId=" + dvdId +
                    ", title='" + title + '\'' +
                    ", genre='" + genre + '\'' +
                    ", releaseDate=" + releaseDate +
                    ", price=" + price +
                    ", isAvailable=" + isAvailable +
                    '}';
        } else {
            CsvFileManager csv= new CsvFileManager();
            Map<Integer, RentalUser> mapUser = csv.importData().getUsers();

            RentalUser user = mapUser.get(userId);
            Map<Integer, RentalUser> output = mapUser.entrySet().stream()
                    .filter(map -> map.getValue().getUserId() == userId)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            return "DVD{" +
                    "dvdId=" + dvdId +
                    ", title='" + title + '\'' +
                    ", genre='" + genre + '\'' +
                    ", releaseDate=" + releaseDate +
                    ", price=" + price +
                    ", isAvailable=" + isAvailable +
                    ", userId=" + userId +
                    ", rentedBy="+ output+
                    '}';


        }
    }
}
