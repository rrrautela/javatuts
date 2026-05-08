package SolidPrinciples;
/**
 * SRP Principle:
 * A class should have only one reason to change.
 * One Class = One Responsibility.
 */

/* * THE BAD WAY (Violating SRP)
 * ----------------------------
 * class Employee {
    * public void calculateSalary() { /* Logic for salary * / }
    * public void generateReport() { /* Logic for printing/PDF * / }
    * public void saveToDatabase() { /* Logic for DB connection * / }
    * * // PROBLEM: Too many reasons to break!
    * // If DB changes, Tax rules change, or Report layout changes, this class breaks.
 * }
 */

// THE GOOD WAY (Following SRP)
// ----------------------------

// Responsibility 1: Business Logic only
class SalaryCalculator {
    public double calculate(double hours, double rate) {
        return hours * rate;
    }
}

// Responsibility 2: Presentation/Reporting only
class SalaryReportGenerator {
    public void printReport(double salary) {
        System.out.println("The calculated salary is: " + salary);
    }
}

// Responsibility 3: Persistence only
class EmployeeRepository {
    public void save() {
        System.out.println("Saving to Database...");
    }
}

public class SRP {
    public static void main(String[] args) {
        SalaryCalculator calc = new SalaryCalculator();
        SalaryReportGenerator reporter = new SalaryReportGenerator();
        EmployeeRepository repo = new EmployeeRepository();

        double total = calc.calculate(40, 50);
        reporter.printReport(total);
        repo.save();
    }
}