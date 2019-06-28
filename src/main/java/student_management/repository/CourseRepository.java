package student_management.repository;

import student_management.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    private static String dbUrl = "jdbc:mysql://localhost:3306/school?serverTimezone=UTC";
    private static String dbUser = "";
    private static String dbPassword = "";

    public static List<Course> getCourses() throws Exception {

        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM COURSE");

        List<Course> courses = parseResultSetToCourses(resultSet);

        connection.close();
        statement.close();
        resultSet.close();

        return courses;


    }

    private static List<Course> parseResultSetToCourses(ResultSet resultSet) throws SQLException {

        List<Course> courses = new ArrayList<>();

        while (resultSet.next()) {

            Course course = new Course();

            course.setCourseId(resultSet.getInt("course_id"));
            course.setCourseName(resultSet.getString("course_name"));
            course.setCredits(resultSet.getInt("credits"));
            course.setTeacherName(resultSet.getString("teacher_name"));

            courses.add(course);
        }
        return courses;
    }

    public static void upDateStudentCourses(int studentID, int courseID) throws SQLException {

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO enrolled (courseID, studentID) VALUES " +
                "(?,?)");

        // what about date ? get current date there?

        prepStmt.setLong(1, courseID);
        prepStmt.setInt(2, studentID);

        int rowsAffected = prepStmt.executeUpdate();
        System.out.println("Successfully updated " + rowsAffected + " student.");

        conn.close();
        prepStmt.close();
    }

    public static void updateCourseDetails(Course course) throws SQLException {

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement prepStmt = conn.prepareStatement("UPDATE course SET course_name = ?, credits = ?, " +
                "teacher_name = ? where course_id = ?");

        prepStmt.setString(1, course.getCourseName());
        prepStmt.setInt(2, course.getCredits());
        prepStmt.setString(3, course.getTeacherName());
        prepStmt.setInt(4, course.getCourseId());

        int rowsAffected = prepStmt.executeUpdate();
        System.out.println("Successfully updated " + rowsAffected + " course.");

        conn.close();
        prepStmt.close();
    }

    public static void addCourse(Course courseToAdd) throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO course (course_name, credits," +
                "teacher_name) VALUES (?,?,?)");
        preparedStatement.setString(1, courseToAdd.getCourseName());
        preparedStatement.setInt(2, courseToAdd.getCredits());
        preparedStatement.setString(3, courseToAdd.getTeacherName());

        int rowsAffected = preparedStatement.executeUpdate();

        System.out.println("Added: " + rowsAffected + " course successfully");

        connection.close();
        preparedStatement.close();

    }

    public static List<Course> getCourseByName(String courseName) throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM course WHERE course_name = ?");

        preparedStatement.setString(1, courseName);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Course> courses = parseResultSetToCourses(resultSet);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return courses;

    }

    public static void deleteCourse(int courseId) throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM course WHERE course_ID = ?");

        preparedStatement.setInt(1, courseId);

        int rowsAffected = preparedStatement.executeUpdate();

        System.out.println("Successfully deleted " + rowsAffected  + " course(s).");

        connection.close();
        preparedStatement.close();


    }
}
