package student_management.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Course {

    private int courseId;
    private String courseName;
    private int credits;
    private String teacherName;
}
