package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    String id;
    String title;
    String name;
    LocalDate date;
    Double salary;
    String address;
    String city;
    String province;
    String postalCode;

}


