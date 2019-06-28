package student_management.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentExtraInfo {

    private Student student;
    private Enrolled enrolled;
    private Course course;


}
