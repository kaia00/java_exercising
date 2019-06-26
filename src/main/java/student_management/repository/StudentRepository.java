package student_management.repository;

import student_management.model.Student;

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

    public static List<String> getStudentAndCoursesByName(String firstName, String lastName) throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student INNER JOIN " +
                "enrolled ON student.student_ID = enrolled.studentID INNER JOIN course ON course.course_ID =" +
                " enrolled.courseID WHERE student_name = ? AND student_surname = ?");
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> students = new ArrayList<>();

        while(resultSet.next()){

            students.add(resultSet.getString("Student_name"));
            students.add(resultSet.getString("Student_surname"));
            students.add(resultSet.getString("Birth_date"));
            students.add(resultSet.getString("Gender"));
            students.add(String.valueOf(resultSet.getInt("Grade")));
            students.add(String.valueOf(resultSet.getInt("Student_ID")));
            students.add(String.valueOf(resultSet.getInt("enrolledID")));
            students.add(String.valueOf(resultSet.getInt("studentID")));
            students.add(String.valueOf(resultSet.getInt("courseID")));
            students.add(resultSet.getString("enrolledDate"));
            students.add(String.valueOf(resultSet.getInt("course_ID")));
            students.add(resultSet.getString("course_Name"));
            students.add(String.valueOf(resultSet.getInt("credits")));
            students.add(resultSet.getString("teacher_Name"));

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
}
