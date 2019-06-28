package student_management.repository;

import student_management.model.Course;
import student_management.model.Enrolled;
import student_management.model.Student;
import student_management.model.StudentExtraInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private static String dbUrl = "jdbc:mysql://localhost:3306/school?serverTimezone=UTC";
    private static String dbUser = "root";
    private static String dbPassword = "e2b9t9mc";


    public static List<Student> getStudents() throws Exception {

        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM STUDENT");

        List<Student> students = parseResultSetToStudents(resultSet);

        connection.close();
        statement.close();
        resultSet.close();

        return students;
    }

    private static List<Student> parseResultSetToStudents(ResultSet resultSet) throws SQLException {

        List<Student> students = new ArrayList<>();

        while (resultSet.next()) {

            Student student = new Student();

            student.setName(resultSet.getString("Student_name"));
            student.setSurname(resultSet.getString("Student_surname"));
            student.setBirthDate(resultSet.getString("Birth_date"));
            student.setGender(resultSet.getString("Gender"));
            student.setGrade(resultSet.getInt("Grade"));
            student.setId(resultSet.getInt("Student_ID"));

            students.add(student);
        }
        return students;
    }

    public static List<Student> getStudentByName(String firstName, String lastName) throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM STUDENT WHERE " +
                "student_name = ? AND student_surname = ? ");
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Student> students = parseResultSetToStudents(resultSet);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return students;

    }

    public static List<StudentExtraInfo> getStudentAndCoursesByName(String firstName, String lastName) throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student LEFT JOIN " +
                "enrolled ON student.student_ID = enrolled.studentID LEFT JOIN course ON course.course_ID =" +
                " enrolled.courseID WHERE student_name = ? AND student_surname = ?");
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<StudentExtraInfo> students = new ArrayList<>();

        while(resultSet.next()){

            StudentExtraInfo studentExtraInfo = new StudentExtraInfo();

            Student student = new Student();

            student.setName(resultSet.getString("Student_name"));
            student.setSurname(resultSet.getString("Student_surname"));
            student.setBirthDate(resultSet.getString("Birth_date"));
            student.setGender(resultSet.getString("Gender"));
            student.setGrade(resultSet.getInt("Grade"));
            student.setId(resultSet.getInt("Student_ID"));


            Enrolled enrolled = new Enrolled();

            enrolled.setEnrolledId(resultSet.getInt("enrolledID"));
            enrolled.setStudentId(resultSet.getInt("studentID"));
            enrolled.setCourseId(resultSet.getInt("courseID"));
            enrolled.setEnrolledDate(resultSet.getString("enrolledDate"));


            Course course = new Course();

            course.setCourseId(resultSet.getInt("course_ID"));
            course.setCourseName(resultSet.getString("course_Name"));
            course.setCredits(resultSet.getInt("credits"));
            course.setTeacherName(resultSet.getString("teacher_Name"));

            studentExtraInfo.setStudent(student);
            studentExtraInfo.setCourse(course);
            studentExtraInfo.setEnrolled(enrolled);

            students.add(studentExtraInfo);

        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return students;
    }

    public static void addStudent(Student student) throws SQLException {

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO student (student_name, student_surname," +
                " birth_date, gender, grade) " +
                "VALUES (?, ?, ?, ?, ?)");

        prepStmt.setString(1, student.getName());
        prepStmt.setString(2, student.getSurname());
        prepStmt.setString(3, student.getBirthDate());
        prepStmt.setString(4, student.getGender());
        prepStmt.setInt(5, student.getGrade());

        int rowsAffected = prepStmt.executeUpdate();
        System.out.println("Added: " + rowsAffected + " student successfully");

        conn.close();
        prepStmt.close();
    }

    public static void upDateStudent(Student student) throws SQLException {

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement prepStmt = conn.prepareStatement("UPDATE student SET student_name = ?, student_surname = ?, " +
                "grade = ? where student_id = ?");

        prepStmt.setString(1, student.getName());
        prepStmt.setString(2, student.getSurname());
        prepStmt.setInt(3, student.getGrade());
        prepStmt.setInt(4, student.getId());

        int rowsAffected = prepStmt.executeUpdate();
        System.out.println("Successfully updated " + rowsAffected + " student.");

        conn.close();
        prepStmt.close();
    }


    public static void deleteStudent(int studentID) throws SQLException {

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement prepStmt = conn.prepareStatement("DELETE FROM student WHERE student_id = ?");

        prepStmt.setInt(1, studentID);

        int rowsAffected = prepStmt.executeUpdate();
        System.out.println("Number of students deleted: " + rowsAffected);

        conn.close();
        prepStmt.close();
    }
}
