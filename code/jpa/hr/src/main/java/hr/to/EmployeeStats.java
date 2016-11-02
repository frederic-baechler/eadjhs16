package hr.to;

import hr.Address;

public class EmployeeStats {

    private final String name;
    private final double salary;
    private final Address address;

    public final static String SELECT_NEW = "SELECT NEW " + EmployeeStats.class.getName() + "(e.name, e.salary, e.address) FROM Employee e";

    public EmployeeStats(String name, double salary, Address address) {
        this.name = name;
        this.salary = salary;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "EmployeeStats{" + "name=" + name + ", salary=" + salary + '}';
    }

}
