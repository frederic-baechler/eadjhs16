package hr;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractTest {

    protected static EntityManagerFactory emf;
    protected static EntityManager em;

    protected Integer employeeId;
    protected Integer bossId;
    protected Integer departmentId;
    protected Integer projectId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("hr");
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @Before
    public void setUp() {
        try {
            em.getTransaction().begin();

            Employee emp = new Employee();
            emp.setName("Peter Muster");
            emp.setSalary(80000);
            em.persist(emp);

            Employee chef = new Employee();
            chef.setName("Roger Federer");
            chef.setSalary(2000000);
            chef.getDirects().add(emp);
            em.persist(chef);

            emp.setBoss(chef);

            Phone phone = new Phone();
            phone.setNumber("031 999 99 99");
            phone.setType(PhoneType.WORK);

            emp.addPhone(phone);

            Department department = new Department();
            department.setName("PR");
            em.persist(department);

            emp.setDepartment(department);

            Address address = new Address();
            address.setCity("Bern");
            address.setState("BE");
            address.setStreet("Frankenstrasse 70");
            address.setZip("3018");

            emp.setAddress(address);

            Project project = new Project();
            project.setName("Arcos");
            project.getEmployees().add(emp);
            em.persist(project);

            emp.getProjects().add(project);

            em.getTransaction().commit();

            employeeId = emp.getId();
            bossId = chef.getId();
            departmentId = department.getId();
            projectId = project.getId();

            em.clear();
            emf.getCache().evictAll();

        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @After
    public void tearDown() {
        try {
            em.getTransaction().begin();

            Employee emp = em.find(Employee.class, employeeId);
            emp.setBoss(null);
            em.remove(emp);

            Employee chef = em.find(Employee.class, bossId);
            if (chef != null) {
                em.remove(chef);
            }
            Department department = em.find(Department.class, departmentId);
            if (department != null) {
                em.remove(department);
            }
            Project project = em.find(Project.class, projectId);
            if (project != null) {
                em.remove(project);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
}
