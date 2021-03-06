package hr;

import hr.to.EmployeeTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;

@NamedQueries({
    @NamedQuery(name = Employee.FIND_BY_NAME, query = "select e from Employee e where e.name = :name"),
    @NamedQuery(name = Employee.AVG, query = "select AVG(e.salary) from Employee e"),
    @NamedQuery(name = "Employee.findByDepartmentName", query = "select e from Employee e where e.department.name = :departmentName")
})
@SqlResultSetMapping(name = "EmployeeTO",
        classes = {@ConstructorResult(targetClass = EmployeeTO.class,
                    columns = {@ColumnResult(name = "id"), @ColumnResult(name = "name")})}
)
@NamedEntityGraph(name = "includePhones", attributeNodes = {@NamedAttributeNode("phones")})
@Entity
public class Employee {

    public final static String FIND_BY_NAME = "Employee.findByName";
    public final static String AVG = "Employee.avg";

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private double salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Address address;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Phone> phones = new HashSet<>();

    @ManyToOne
    private Department department;

    @ManyToMany(mappedBy = "employees")
    private List<Project> projects = new ArrayList<>();

    @ManyToOne
    private Employee boss;

    @OneToMany(mappedBy = "boss")
    private Set<Employee> directs = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Employee getBoss() {
        return boss;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public Set<Employee> getDirects() {
        return directs;
    }

    public void setDirects(Set<Employee> directs) {
        this.directs = directs;
    }

    public void addPhone(Phone phone) {
        phone.setEmployee(this);
        this.phones.add(phone);
    }

    public void removePhone(Phone phone) {
        phone.setEmployee(null);
        this.phones.remove(phone);
    }

    public void addDirect(Employee employee) {
        employee.setBoss(this);
        directs.add(employee);
    }

    public void removeDirect(Employee employee) {
        employee.setBoss(null);
        directs.remove(employee);
    }
}
