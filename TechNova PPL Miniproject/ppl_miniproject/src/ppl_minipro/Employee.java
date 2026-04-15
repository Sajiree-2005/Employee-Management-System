package ppl_minipro;

public class Employee {
    protected String name;
    protected int id;
    protected double salary;
    protected String domain;

    public Employee(String name, int id, double salary, String domain) {
        this.name = name;
        this.id = id;
        this.salary = salary;
        this.domain = domain;
    }

    public void displayDetails() {
        System.out.println("Name    : " + name);
        System.out.println("ID      : " + id);
        System.out.println("Salary  : " + salary);
        System.out.println("Domain  : " + domain);
    }

    public int getId()          { return id; }
    public double getSalary()   { return salary; }

    public void setName(String name)       { this.name = name; }
    public void setSalary(double salary)   { this.salary = salary; }
    public void setDomain(String domain)   { this.domain = domain; }
}
