package model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public abstract class User implements CsvConvertible {

    private int userId;
    private String firstName;
    private String lastName;
    private String birthDate;

    public User(String firstName, String lastName, String birthDate,int userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.userId = userId;
    }

    public User(String firstName, String lastName, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public abstract String toXML(RentalUser user);
}
