import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A simple console application for managing employee records.
 */
public class EmployeeManagement {
    private static final Map<Integer, Employee> employees = new LinkedHashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Employee Management ===");
            System.out.println("1. Add employee");
            System.out.println("2. Display all employees");
            System.out.println("3. Search employee by ID");
            System.out.println("4. Update employee details");
            System.out.println("5. Delete employee");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            switch (readInt()) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1–6.");
            }
        }

        scanner.close();
    }

    private static void addEmployee() {
        System.out.print("Employee ID: ");
        int id = readInt();
        if (employees.containsKey(id)) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        System.out.print("Name: ");
        String name = readNonBlankLine("Name cannot be blank. Enter name: ");
        System.out.print("Department: ");
        String department = readNonBlankLine("Department cannot be blank. Enter department: ");
        System.out.print("Salary: ");
        double salary = readSalary();

        employees.put(id, new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees have been added yet.");
            return;
        }

        printTableHeader();
        for (Employee employee : employees.values()) {
            employee.display();
        }
    }

    private static void searchEmployee() {
        System.out.print("Enter employee ID to search: ");
        int id = readInt();
        Employee employee = employees.get(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
        } else {
            printTableHeader();
            employee.display();
        }
    }

    private static void updateEmployee() {
        System.out.print("Enter employee ID to update: ");
        int id = readInt();
        Employee employee = employees.get(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        System.out.println("Enter the updated details for employee ID " + id + ".");
        System.out.print("Name: ");
        String name = readNonBlankLine("Name cannot be blank. Enter name: ");
        System.out.print("Department: ");
        String department = readNonBlankLine("Department cannot be blank. Enter department: ");
        System.out.print("Salary: ");
        double salary = readSalary();

        employee.update(name, department, salary);
        System.out.println("Employee details updated successfully.");
    }

    private static void deleteEmployee() {
        System.out.print("Enter employee ID to delete: ");
        int id = readInt();
        Employee removed = employees.remove(id);

        if (removed == null) {
            System.out.println("No employee found with ID " + id + ".");
        } else {
            System.out.println("Employee " + id + " deleted successfully.");
        }
    }

    private static void printTableHeader() {
        System.out.println("\nID | Name | Department | Salary");
        System.out.println("-----------------------------------------------");
    }

    private static int readInt() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.print("Please enter a valid whole number: ");
            }
        }
    }

    private static double readSalary() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                double salary = Double.parseDouble(input);
                if (Double.isFinite(salary) && salary >= 0) {
                    return salary;
                }
            } catch (NumberFormatException ignored) {
                // Prompt again below.
            }
            System.out.print("Enter a valid non-negative salary: ");
        }
    }

    private static String readNonBlankLine(String retryPrompt) {
        String value = scanner.nextLine().trim();
        while (value.isEmpty()) {
            System.out.print(retryPrompt);
            value = scanner.nextLine().trim();
        }
        return value;
    }

    private static class Employee {
        private final int id;
        private String name;
        private String department;
        private double salary;

        private Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        private void update(String name, String department, double salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        private void display() {
            System.out.printf("%d | %s | %s | %.2f%n", id, name, department, salary);
        }
    }
}
