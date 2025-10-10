import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

// ------------------------------------
// 1. Grade Class (Aggregated Object)
// ------------------------------------
class Grade {
    private String testName;
    private double score; // e.g., 0.0 to 100.0

    public Grade(String testName, double score) {
        this.testName = testName;
        this.score = score;
    }

    public double getScore() {
        return score;
    }
    public String getTestName() {
        return testName;
    }
}

// ------------------------------------
// 2. Student Class (Core Entity)
// ------------------------------------
class Student {
    private String name;
    private int age;
    private String studentID;
    // AGGREGATION: Student HAS-A List of Grades
    private List<Grade> grades; 

    public Student(String name, int age, String studentID) {
        this.name = name;
        this.age = age;
        this.studentID = studentID;
        this.grades = new ArrayList<>();
    }

    // Method to add a grade/test score
    public void addGrade(String testName, double score) {
        this.grades.add(new Grade(testName, score));
    }

    // Method to calculate the average grade
    public double calculateAverage() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Grade grade : grades) {
            sum += grade.getScore();
        }
        return sum / grades.size();
    }

    // Method to assign a letter grade
    public String getLetterGrade() {
        double avg = calculateAverage();
        if (avg >= 90) return "A";
        if (avg >= 80) return "B";
        if (avg >= 70) return "C";
        if (avg >= 60) return "D";
        return "F";
    }

    // Getters for student information (used for display and lookup)
    public String getStudentID() {
        return studentID;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public List<Grade> getGrades() {
        return grades;
    }

    // Overriding toString for easy viewing
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Age: %d | Avg Score: %.2f | Letter Grade: %s",
                             studentID, name, age, calculateAverage(), getLetterGrade());
    }
}

// ------------------------------------
// 3. Main Management Class
// ------------------------------------
public class StudentManagementSystem {
    // Stores students using a HashMap for efficient ID lookup
    private Map<String, Student> studentDatabase; 
    private Scanner scanner;

    public StudentManagementSystem() {
        this.studentDatabase = new HashMap<>();
        this.scanner = new Scanner(System.in);
        // Initialize with sample data
        initializeData();
    }

    private void initializeData() {
        Student s1 = new Student("Alice Johnson", 20, "S1001");
        s1.addGrade("Midterm", 85.5);
        s1.addGrade("Final", 92.0);
        studentDatabase.put(s1.getStudentID(), s1);

        Student s2 = new Student("Bob Smith", 21, "S1002");
        s2.addGrade("Quiz", 65.0);
        s2.addGrade("Project", 78.5);
        studentDatabase.put(s2.getStudentID(), s2);
        System.out.println("System initialized with two sample students.");
    }

    // --- CRUD Operations ---

    // 1. Add Student (Create)
    public void addStudent() {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        if (studentDatabase.containsKey(id)) {
            System.out.println("❌ Error: Student ID already exists. Try again.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        int age = -1;
        try {
            System.out.print("Enter Age: ");
            age = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid age entered. Returning to menu.");
            return;
        }

        Student newStudent = new Student(name, age, id);
        studentDatabase.put(id, newStudent);
        System.out.println("✅ Student " + name + " added successfully.");
    }

    // 2. View All Students (Read)
    public void viewAllStudents() {
        System.out.println("\n--- All Students in System (" + studentDatabase.size() + ") ---");
        if (studentDatabase.isEmpty()) {
            System.out.println("No students currently in the system.");
            return;
        }
        for (Student s : studentDatabase.values()) {
            System.out.println(s);
            // Format grades for display
            String gradeDetails = s.getGrades().stream()
                                   .map(g -> g.getTestName() + ": " + String.format("%.1f", g.getScore()))
                                   .collect(Collectors.joining(" | "));
            System.out.println("  📝 Grades: " + (gradeDetails.isEmpty() ? "No grades recorded." : gradeDetails));
        }
    }

    // 3. Update/Add Grades (Update)
    public void updateStudentGrades() {
        System.out.println("\n--- Update Student Grades ---");
        System.out.print("Enter Student ID to update grades for: ");
        String id = scanner.nextLine();
        Student student = studentDatabase.get(id);

        if (student == null) {
            System.out.println("❌ Error: Student not found with ID " + id);
            return;
        }
        
        System.out.println("Current student: " + student.getName());
        System.out.print("Enter Test Name (e.g., 'Quiz 1', 'Final Exam'): ");
        String testName = scanner.nextLine();
        
        double score = -1;
        try {
            System.out.print("Enter Score (0-100): ");
            score = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid score entered. Returning to menu.");
            return;
        }

        student.addGrade(testName, score);
        System.out.println("✅ Grade (" + testName + ": " + score + ") added for " + student.getName() + ".");
        System.out.println("New Average Grade: " + String.format("%.2f", student.calculateAverage()));
    }

    // 4. Delete Student (Delete)
    public void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine();

        if (studentDatabase.remove(id) != null) {
            System.out.println("✅ Student with ID " + id + " deleted successfully.");
        } else {
            System.out.println("❌ Error: Student not found with ID " + id);
        }
    }

    // --- Main Menu and Execution ---

    public void run() {
        int choice;
        do {
            System.out.println("\n=========================================");
            System.out.println("  📚 Student Management & Grading System 📝");
            System.out.println("=========================================");
            System.out.println("1. Add New Student (C)");
            System.out.println("2. View All Students & Grades (R)");
            System.out.println("3. Update/Add Student Grades (U)");
            System.out.println("4. Delete Student (D)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                // Use nextLine and parse for safer input handling
                String input = scanner.nextLine();
                if (input.isEmpty()) continue;
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1: addStudent(); break;
                    case 2: viewAllStudents(); break;
                    case 3: updateStudentGrades(); break;
                    case 4: deleteStudent(); break;
                    case 5: System.out.println("Exiting System. Goodbye! 👋"); break;
                    default: System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                choice = 0; // Keep loop running
            }
        } while (choice != 5);
        scanner.close();
    }

    // Main method to run the program
    public static void main(String[] args) {
        new StudentManagementSystem().run();
    }
}
