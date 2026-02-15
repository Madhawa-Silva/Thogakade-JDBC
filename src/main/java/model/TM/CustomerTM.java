package model.TM;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CustomerTM {
    String id;
    String title;
    String name;
    LocalDate dob;
    Double salary;
    String address;
    String city;
    String province;
    String postalCode;

    public CustomerTM(String id, String title , String name, LocalDate dob, Double salary, String address, String city, String province, String postalCode) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.dob = dob;
        this.salary = salary;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }

    public String getFullName() {
        return title + "." + name;

    }

    public String getFullCity() {
        return city + " - " + postalCode;
    }
}
