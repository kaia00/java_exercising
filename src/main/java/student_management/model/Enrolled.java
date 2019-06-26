package student_management.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Enrolled {

    private int enrolledId;
    private int studentId;
    private int courseId;
    private String enrolledDate;
}
