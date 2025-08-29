import java.util.Scanner;

class Employee {
    private int id;
    private String name;
    protected double salary;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void calculateSalary() {
    }

    public void displayDetails() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Salary: " + salary);
    }
}

class FullTimeEmployee extends Employee {
    private double monthlySalary;

    public FullTimeEmployee(int id, String name, double monthlySalary) {
        super(id, name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public void calculateSalary() {
        salary = monthlySalary;
    }
}

class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(int id, String name, int hoursWorked, double hourlyRate) {
        super(id, name);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public void calculateSalary() {
        salary = hoursWorked * hourlyRate;
    }
}

public class PayrollSystem {
    public static void main(String[] args) {
        FullTimeEmployee fullTime = new FullTimeEmployee(1, "rahul", 50000);
        PartTimeEmployee partTime = new PartTimeEmployee(2, "ram", 80, 200);

        fullTime.calculateSalary();
        partTime.calculateSalary();

        System.out.println("Full-Time Employee Details:");
        fullTime.displayDetails();

        System.out.println("\nPart-Time Employee Details:");
        partTime.displayDetails();
    }
}
