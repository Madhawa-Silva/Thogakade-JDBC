package model.TM;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CustomerTM {
    private String id;
    private String name;
    private String address;
    private Date dob;
    private Double salary;
    private String city;
    private String province;

    public CustomerTM(String id, String title , String name, Date dob, Double salary, String address, String city, String province, String postalCode) {
        this.id = id;
        this.name = title+"."+name;
        this.address = address;
        this.dob = dob;
        this.salary = salary;
        this.city = city+" - "+postalCode;
        this.province = province;
    }
}
