import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * The Student class represents a single student in the system,
 * containing personal details and academic grades. It handles the
 * core grading logic (average calculation and letter assignment).
 * This demonstrates the concept of encapsulation.
 */
class Student {
    // Attributes
    private int id;
    private String name;
    private int age;
    // Aggregation: A Student aggregates a list of grades (scores)
    private List<Double> grades; 

    // Constructor
    public Student(int id, String name, int age, List<Double> grades) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grades = grades;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Double> getGrades() {
        return grades;
    }

    // Setters (for update functionality)
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGrades(List<Double> grades) {
        this.grades = grades;
    }

    /**
     * Calculates the average of all grades stored for the student.
     * @return The average score as a double, or 0.0 if no grades exist.
     */
    public double calculateAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    /**
     * Assigns a letter grade based on the calculated average score.
     * This is a simple description of the grading criteria.
     * A: 90-100, B: 80-89, C: 70-79, D: 60-69, F: <60.
     * @return The letter grade (A, B, C, D, or F).
     */
    public String assignLetterGrade() {
        double average = calculateAverageGrade();
        if (average >= 90) return "A";
        if (average >= 80) return "B";
        if (average >= 70) return "C";
        if (average >= 60) return "D";
        return "F";
    }

    /**
     * Provides a detailed string representation of the Student object.
     * @return Formatted student details including calculated grade.
     */
    @Override
    public String toString() {
        double average = calculateAverageGrade();
        String letterGrade = assignLetterGrade();
        
        return String.format(
            "| ID: %-5d | Name: %-20s | Age: %-4d | Grades: %-25s | Avg Score: %.2f | Letter Grade: %-2s |",
            id, name, age, grades.toString(), average, letterGrade
        );
    }
}

/**
 * The StudentManagementSystem class acts as the "backend" logic,
 * managing the collection of students and providing the CRUD operations.
 * It also handles the "front-end" console interface and authentication.
 * This class demonstrates aggregation by containing an ArrayList of Student objects.
 */
public class StudentManagementSystem {
    
    // Aggregation: The system holds a collection of Student objects
    private List<Student> studentList;
    private Scanner scanner;
    
    // Simple hardcoded user credentials for the "front-end" login
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password123";

    // Counter to ensure unique IDs
    private int nextStudentId = 1001; 

    public StudentManagementSystem() {
        this.studentList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        // Add some initial data for testing
        addSampleStudents();
    }

    /**
     * Adds initial sample students to demonstrate the system functionality.
     */
    private void addSampleStudents() {
        studentList.add(new Student(nextStudentId++, "Alice Smith", 19, List.of(95.0, 88.0, 92.0)));
        studentList.add(new Student(nextStudentId++, "Bob Johnson", 20, List.of(75.0, 68.0, 81.0)));
        studentList.add(new Student(nextStudentId++, "Charlie Brown", 18, List.of(55.0, 62.0, 40.0)));
        System.out.println("System initialized with " + studentList.size() + " sample students.");
    }
    
    /**
     * Handles the authentication check.
     * @return true if credentials are valid, false otherwise.
     */
    private boolean authenticate() {
        System.out.println("\n--- SYSTEM LOGIN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine(); // Note: In a real app, this should hide input

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("Login successful! Welcome, " + username + ".");
            return true;
        } else {
            System.out.println("Login failed. Invalid credentials.");
            return false;
        }
    }

    // --- CRUD Operations ---

    /**
     * Prompts the user for student details and adds a new Student object to the list.
     */
    public void addStudent() {
        try {
            System.out.println("\n--- ADD NEW STUDENT ---");
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            System.out.print("Enter student age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter grades (e.g., 85.0, 92.5, 78): ");
            String gradesInput = scanner.nextLine();
            List<Double> grades = parseGrades(gradesInput);

            Student newStudent = new Student(nextStudentId, name, age, grades);
            studentList.add(newStudent);
            System.out.println("\nSUCCESS: Student added with ID: " + nextStudentId);
            nextStudentId++;
        } catch (InputMismatchException e) {
            System.err.println("\nERROR: Invalid input format. Please ensure age is a number.");
            scanner.nextLine(); // Clear the buffer
        } catch (Exception e) {
            System.err.println("\nAn unexpected error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Displays all students, their average scores, and letter grades.
     */
    public void viewAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("\nNo students registered in the system.");
            return;
        }

        System.out.println("\n--- ALL REGISTERED STUDENTS & GRADES ---");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        for (Student student : studentList) {
            System.out.println(student); // Calls Student.toString()
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Finds a student by ID and allows the user to update their details or grades.
     */
    public void updateStudent() {
        try {
            System.out.println("\n--- UPDATE STUDENT ---");
            System.out.print("Enter student ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Student studentToUpdate = findStudentById(id);
            
            if (studentToUpdate == null) {
                System.out.println("ERROR: Student with ID " + id + " not found.");
                return;
            }

            System.out.println("Found Student: " + studentToUpdate.getName());
            System.out.println("Which field to update? (1: Name, 2: Age, 3: Grades, 0: Cancel)");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    studentToUpdate.setName(scanner.nextLine());
                    System.out.println("SUCCESS: Name updated.");
                    break;
                case 2:
                    System.out.print("Enter new age: ");
                    studentToUpdate.setAge(scanner.nextInt());
                    scanner.nextLine();
                    System.out.println("SUCCESS: Age updated.");
                    break;
                case 3:
                    System.out.print("Enter new grades (e.g., 85.0, 92.5, 78): ");
                    String gradesInput = scanner.nextLine();
                    studentToUpdate.setGrades(parseGrades(gradesInput));
                    System.out.println("SUCCESS: Grades updated.");
                    break;
                case 0:
                    System.out.println("Update cancelled.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (InputMismatchException e) {
            System.err.println("\nERROR: Invalid input format. ID and Age must be numbers.");
            scanner.nextLine(); // Clear the buffer
        } catch (Exception e) {
            System.err.println("\nAn unexpected error occurred during update: " + e.getMessage());
        }
    }

    /**
     * Finds a student by ID and removes them from the list.
     */
    public void deleteStudent() {
        try {
            System.out.println("\n--- DELETE STUDENT ---");
            System.out.print("Enter student ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Student studentToDelete = findStudentById(id);
            
            if (studentToDelete == null) {
                System.out.println("ERROR: Student with ID " + id + " not found.");
                return;
            }

            System.out.print("Are you sure you want to delete " + studentToDelete.getName() + " (Y/N)? ");
            String confirmation = scanner.nextLine().toUpperCase();
            
            if (confirmation.equals("Y")) {
                studentList.remove(studentToDelete);
                System.out.println("SUCCESS: Student " + studentToDelete.getName() + " deleted.");
            } else {
                System.out.println("Deletion cancelled.");
            }

        } catch (InputMismatchException e) {
            System.err.println("\nERROR: Invalid input format. ID must be a number.");
            scanner.nextLine(); // Clear the buffer
        }
    }

    // --- Helper Methods ---
    
    /**
     * Searches the list for a student by ID.
     * @param id The ID to search for.
     * @return The Student object or null if not found.
     */
    private Student findStudentById(int id) {
        for (Student student : studentList) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    /**
     * Parses a comma-separated string of grades into a List of Doubles.
     * @param gradesInput The string input (e.g., "80, 90.5, 75").
     * @return A list of Double grades.
     */
    private List<Double> parseGrades(String gradesInput) {
        List<Double> grades = new ArrayList<>();
        // Split by comma, trim whitespace, and parse to Double
        String[] scores = gradesInput.split(",");
        for (String score : scores) {
            try {
                grades.add(Double.parseDouble(score.trim()));
            } catch (NumberFormatException e) {
                System.err.println("Warning: Skipping invalid grade input: " + score);
            }
        }
        return grades;
    }


    /**
     * Main application loop and menu display.
     */
    public void run() {
        boolean loggedIn = authenticate();

        if (!loggedIn) {
            return; // Exit if authentication fails
        }

        int choice = -1;
        while (choice != 0) {
            displayMenu();
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        updateStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 4:
                        viewAllStudents();
                        break;
                    case 0:
                        System.out.println("\nExiting Student Management System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println("\nInvalid input. Please enter a number for your choice.");
                scanner.nextLine(); // Clear the buffer
                choice = -1; // Reset choice to keep loop running
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=======================================================");
        System.out.println("  STUDENT MANAGEMENT & GRADING SYSTEM (Console Menu) ");
        System.out.println("=======================================================");
        System.out.println("1. Add New Student");
        System.out.println("2. Update Student Details");
        System.out.println("3. Delete Student");
        System.out.println("4. View All Students (with Grades)");
        System.out.println("0. Exit System");
        System.out.println("-------------------------------------------------------");
    }

    /**
     * Main entry point for the program.
     */
    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.run();
    }
}
