package student_management;

import student_management.model.Student;
import student_management.repository.StudentRepository;

import java.util.List;
import java.util.Scanner;

public class Student_Management {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the student management system. You have the following options:");
        System.out.println("1. View all students");
        System.out.println("2. View students by name"); // view students OR students and their courses.
        System.out.println("3. View all courses");
        System.out.println("4. View courses by name");

        System.out.println("5. Add student");
        System.out.println("6. Add course");

        System.out.println("7. Update student/course"); // choose which one to update
        System.out.println("8. Delete student/course"); // choose which one to delete

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
                   List<String> studentsStringList = StudentRepository.getStudentAndCoursesByName(firstName, lastName);
                    for (String student : studentsStringList) {
                        System.out.print(student + ", ");
                    }

                } else {
                    System.out.println("Invalid choice");
                }
                break;
            case 3:


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
                System.out.println("Grade:");
                student.setGrade(scanner.nextInt());

                System.out.println();

                StudentRepository.addStudent(student);

                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;

            default:
                System.out.println("Invalid choice");
        }
    }
}
