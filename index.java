import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Represents a single test or assignment grade.
 * Equivalent to the JavaScript 'Grade' class.
 */
class Grade {
    private String testName;
    private double score;

    public Grade(String testName, double score) {
        this.testName = testName;
        this.score = score;
    }

    public String getTestName() {
        return testName;
    }

    public double getScore() {
        return score;
    }

    // Mirrors the JavaScript getGradeDetails part
    @Override
    public String toString() {
        return testName + ": " + String.format("%.1f", score);
    }
}

/**
 * Represents a student with personal details and a list of grades.
 * Equivalent to the JavaScript 'Student' class.
 */
class Student {
    private String name;
    private int age;
    private String studentID;
    private List<Grade> grades;

    public Student(String name, int age, String id) {
        this.name = name;
        this.age = age;
        this.studentID = id;
        this.grades = new ArrayList<>();
    }

    // Getters and Setters for CRUD updates
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStudentID() {
        return studentID;
    }

    public void addGrade(String testName, double score) {
        this.grades.add(new Grade(testName, score));
    }

    // Equivalent to JavaScript's calculateAverage()
    public double calculateAverage() {
        if (this.grades.isEmpty()) return 0.0;
        double sum = this.grades.stream().mapToDouble(Grade::getScore).sum();
        return sum / this.grades.size();
    }

    // Equivalent to JavaScript's getLetterGrade()
    public String getLetterGrade() {
        double avg = calculateAverage();
        if (avg >= 90) return "A";
        if (avg >= 80) return "B";
        if (avg >= 70) return "C";
        if (avg >= 60) return "D";
        return "F";
    }

    // Equivalent to JavaScript's getGradeDetails()
    public String getGradeDetails() {
        return this.grades.stream()
            .map(Grade::toString)
            .collect(Collectors.joining(" | "));
    }

    // For display in the console
    public void printDetails() {
        System.out.printf("| %-8s | %-15s | %-4d | %-8.2f | %-12s | %s\n",
                studentID, name, age, calculateAverage(), getLetterGrade(), getGradeDetails());
    }
}

/**
 * Main application class, managing the database and user interactions.
 * Equivalent to the main script logic in the HTML file.
 */
public class StudentManagementSystem {

    // Equivalent to const VALID_USERNAME & VALID_PASSWORD
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "1234";

    // Equivalent to const studentDatabase = new Map();
    private static final Map<String, Student> studentDatabase = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  Secured Student Management & Grading System (Java Console)");
        System.out.println("=================================================");

        if (handleLogin()) {
            initializeData();
            mainMenu();
        } else {
            System.out.println("Application closed.");
        }
    }

    // ------------------------------------
    // LOGIN/LOGOUT LOGIC (Console Simulation)
    // ------------------------------------

    private static boolean handleLogin() {
        System.out.println("\n--- Student System Login 🔒 ---");
        System.out.print("Username (Hint: admin): ");
        String username = scanner.nextLine().trim();
        System.out.print("Password (Hint: 1234): ");
        String password = scanner.nextLine().trim();

        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            System.out.println("Login successful! Welcome to the system.");
            return true;
        } else {
            System.out.println("Invalid username or password. Login failed.");
            return false;
        }
    }

    private static void handleLogout() {
        System.out.println("\nSuccessfully logged out.");
        // In a console app, logging out typically means exiting or returning to a login loop
        // Here, we'll exit the application
        System.exit(0);
    }

    // ------------------------------------
    // CORE APPLICATION LOGIC (CRUD)
    // ------------------------------------

    private static void mainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add/Update Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Add Test Score");
            System.out.println("4. View All Students (List)");
            System.out.println("5. Logout/Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        addOrUpdateStudent();
                        break;
                    case 2:
                        deleteStudent();
                        break;
                    case 3:
                        addGrade();
                        break;
                    case 4:
                        renderStudents();
                        break;
                    case 5:
                        running = false;
                        handleLogout();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // Equivalent to JavaScript's addStudent()
    private static void addOrUpdateStudent() {
        System.out.println("\n--- Add/Update Student ---");
        System.out.print("ID (for lookup/update): ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Age: ");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age. Operation cancelled.");
            return;
        }

        if (id.isEmpty() || name.isEmpty() || age <= 0) {
            System.out.println("Please fill in all student fields correctly.");
            return;
        }

        if (studentDatabase.containsKey(id)) {
            Student student = studentDatabase.get(id);
            student.setName(name);
            student.setAge(age);
            System.out.printf("Student ID %s updated successfully.\n", id);
        } else {
            Student newStudent = new Student(name, age, id);
            studentDatabase.put(id, newStudent);
            System.out.printf("Student %s added successfully.\n", name);
        }
        renderStudents();
    }

    // Equivalent to JavaScript's deleteStudent()
    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine().trim();

        if (id.isEmpty()) {
            System.out.println("Please enter the Student ID to delete.");
            return;
        }

        if (studentDatabase.remove(id) != null) {
            System.out.printf("Student ID %s deleted successfully.\n", id);
        } else {
            System.out.printf("Error: Student ID %s not found.\n", id);
        }
        renderStudents();
    }

    // Equivalent to JavaScript's addGrade()
    private static void addGrade() {
        System.out.println("\n--- Add Test Score ---");
        System.out.print("Student ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Test Name (e.g., Midterm, Project): ");
        String testName = scanner.nextLine().trim();
        System.out.print("Score (0-100): ");
        double score;
        try {
            score = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid score. Operation cancelled.");
            return;
        }

        if (id.isEmpty() || testName.isEmpty() || score < 0 || score > 100) {
            System.out.println("Please fill in all grade fields correctly (Score 0-100).");
            return;
        }

        Student student = studentDatabase.get(id);
        if (student == null) {
            System.out.printf("Error: Student ID %s not found.\n", id);
            return;
        }

        student.addGrade(testName, score);
        System.out.printf("Score added for %s.\n", student.getName());
        renderStudents();
    }

    // Equivalent to JavaScript's renderStudents()
    private static void renderStudents() {
        System.out.println("\n--- Student List 📊 ---");
        if (studentDatabase.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }

        // Print header
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-8s | %-15s | %-4s | %-8s | %-12s | %s\n", "ID", "Name", "Age", "Average", "Letter Grade", "Grades");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        // Print each student's details
        for (Student student : studentDatabase.values()) {
            student.printDetails();
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }

    // Equivalent to JavaScript's initializeData()
    private static void initializeData() {
        // Clear previous data if any, then add samples
        studentDatabase.clear();

        Student s1 = new Student("Alice Johnson", 20, "S1001");
        s1.addGrade("Midterm", 85.5);
        s1.addGrade("Final", 92.0);
        studentDatabase.put(s1.getStudentID(), s1);

        Student s2 = new Student("Bob Smith", 21, "S1002");
        s2.addGrade("Quiz", 65.0);
        s2.addGrade("Project", 70.0);
        studentDatabase.put(s2.getStudentID(), s2);

        // renderStudents is called in the main menu after initialization
    }
}
