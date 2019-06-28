package student_management;

import student_management.model.Course;
import student_management.model.Student;
import student_management.model.StudentExtraInfo;
import student_management.repository.CourseRepository;
import student_management.repository.StudentRepository;

import java.util.List;
import java.util.Scanner;

public class Student_Management {

    public static void main(String[] args) throws Exception {


        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the student management system. You have the following options:");
        System.out.println("1. View all students");
        System.out.println("2. View students by name");
        System.out.println("3. View all courses");
        System.out.println("4. View courses by name");

        System.out.println("5. Add student");
        System.out.println("6. Add course");

        System.out.println("7. Update student/course");
        System.out.println("8. Delete student/course");


        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Trying to view all students...");

                List<Student> students = StudentRepository.getStudents();
                for (Student student : students) {
                    System.out.println(student + ", ");
                }

                break;
            case 2:
                scanner.nextLine();
                System.out.println("Please enter student first name");
                String firstName = scanner.nextLine();
                System.out.println("Please enter student last name");
               String lastName = scanner.nextLine();
                System.out.println("Do you want to see the student info or their courses also?");
                System.out.println("1. Student info only");
                System.out.println("2. Student info + courses they attend");
                choice = scanner.nextInt();
                if (choice == 1) {
                    students = StudentRepository.getStudentByName(firstName, lastName);
                    for (Student student : students) {
                        System.out.println(student + ", ");
                    }

                } else if (choice == 2) {
                    List<StudentExtraInfo> studentsExtraInfo = StudentRepository.getStudentAndCoursesByName(firstName, lastName);

                    for (StudentExtraInfo student : studentsExtraInfo) {
                        System.out.print(student + ", ");
                    }

                } else {
                    System.out.println("Invalid choice");
                }
                break;
            case 3:
                System.out.println("Trying to view all courses");
                List<Course> courses = CourseRepository.getCourses();
                for (Course course : courses) {
                    System.out.println(course + ", ");
                }


                break;
            case 4:
                scanner.nextLine();
                System.out.println("Write course name to view");
                String courseName = scanner.nextLine();
                courses = CourseRepository.getCourseByName(courseName);
                for (Course course : courses) {
                    System.out.println(course + ", ");
                }

                break;
            case 5:
                scanner.nextLine();
                System.out.println("Student first name:");
                Student student = new Student();
                student.setName(scanner.nextLine());
                System.out.println("Student last name:");
                student.setSurname(scanner.nextLine());
                System.out.println("Birth date(YYYY-MM-DD): ");
                student.setBirthDate(scanner.nextLine());
                System.out.println("Gender: ");
                student.setGender(scanner.next());
                scanner.nextLine();
                System.out.println("Grade:");
                student.setGrade(scanner.nextInt());

                System.out.println();

                StudentRepository.addStudent(student);

                break;
            case 6:
                scanner.nextLine();
                Course courseToAdd = new Course();
                System.out.println("Add course name:");
                courseToAdd.setCourseName(scanner.nextLine());
                System.out.println("Add course credits");
                courseToAdd.setCredits(Integer.parseInt(scanner.nextLine()));
                System.out.println("Add teacher name");
                courseToAdd.setTeacherName(scanner.nextLine());

                CourseRepository.addCourse(courseToAdd);

                break;
            case 7:
                System.out.println("Do you want to update student (1) or course (2)");
                choice = scanner.nextInt();
                if (choice == 1) {
                    scanner.nextLine();
                    System.out.println("Write student name");
                    firstName = scanner.nextLine();
                    lastName = scanner.nextLine();
                    System.out.println("We have following students with that name. Enter student ID you want to update");
                    // if there is no student with that name? Exit program?
                    students = StudentRepository.getStudentByName(firstName, lastName);
                    for (Student student1 : students) {
                        System.out.println(student1 + ", ");
                    }
                    int studentID = scanner.nextInt();
                    System.out.println("Do you want to update student details (1) or add student to courses? (2)");
                    choice = scanner.nextInt();
                    if (choice == 1) {
                        System.out.println("Enter new data for student. Firstname, lastname, grade");
                        scanner.nextLine();
                        Student student1 = new Student();
                        student1.setName(scanner.nextLine());
                        student1.setSurname(scanner.nextLine());
                        student1.setGrade(scanner.nextInt());
                        student1.setId(studentID);

                        StudentRepository.upDateStudent(student1);
                    } else if (choice == 2) {
                        System.out.println("The course list is as follows. Write course ID you want to add");
                        courses = CourseRepository.getCourses();
                        for (Course course : courses) {
                            System.out.println(course + ", ");
                        }
                        int courseID = scanner.nextInt();
                        CourseRepository.upDateStudentCourses(studentID, courseID);

                    } else {
                        System.out.println("Invalid choice");
                    }

                } else if (choice == 2) {
                    System.out.println("What course do you want to update? Write the ID of the course");
                    System.out.println("Trying to view all courses");
                    courses = CourseRepository.getCourses();
                    for (Course course : courses) {
                        System.out.println(course + ", ");
                    }
                    int courseId = scanner.nextInt();
                    scanner.nextLine();
                    Course course = new Course();
                    System.out.println("Write updated course name:");
                    course.setCourseName(scanner.nextLine());
                    System.out.println("Write updated course credits");
                    course.setCredits(scanner.nextInt());
                    scanner.nextLine();
                    System.out.println("Write updated teacher name");
                    course.setTeacherName(scanner.nextLine());
                    course.setCourseId(courseId);
                    CourseRepository.updateCourseDetails(course);

                } else {
                    System.out.println("Invalid choice");
                }
                break;
            case 8:
                System.out.println("Do you want to delete a student (1) or a course (2)");
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {
                    System.out.println("Write student name");
                    firstName = scanner.nextLine();
                    lastName = scanner.nextLine();
                    System.out.println("We have following students with that name. Write ID of the student to delete");
                    students = StudentRepository.getStudentByName(firstName, lastName);
                    for (Student student1 : students) {
                        System.out.println(student1 + ", ");
                    }
                    int studentID = Integer.parseInt(scanner.nextLine());
                    StudentRepository.deleteStudent(studentID);

                } else if (choice == 2) {

                    System.out.println("Following courses available. Write ID of the course to delete");
                    courses = CourseRepository.getCourses();
                    for (Course course : courses) {
                        System.out.println(course + ", ");
                    }
                    int courseId = Integer.parseInt(scanner.nextLine());
                    CourseRepository.deleteCourse(courseId);

                } else {
                    System.out.println("Invalid choice");
                }
                break;

            default:
                System.out.println("Invalid choice");
        }

    }
}
