package student_management.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Student {

    private String name;
    private String surname;
    private String birthDate;
    private String gender;
    private int grade;
    private int id;
}
