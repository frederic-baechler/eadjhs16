package hr.to;

public class EmployeeTO {

    private final Integer id;
    private final String name;

    public EmployeeTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "EmployeeTO{" + "id=" + id + ", name=" + name + '}';
    }

}
