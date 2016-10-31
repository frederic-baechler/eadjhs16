package hr;

import hr.Employee;
import hr.Phone;
import javax.persistence.EntityTransaction;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RelationshipTest extends AbstractTest {

    private Integer employeeId;

    @Before
    public void setUp() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Employee employee = new Employee();
        em.persist(employee);

        Phone phone = new Phone();
        phone.setType(PhoneType.MOBILE);
        phone.setNumber("+41 79 111 22 33");
        em.persist(phone);

        phone.setEmployee(employee);

        transaction.commit();

        employeeId = employee.getId();

        em.clear();
        emf.getCache().evictAll();
    }

    @After
    public void tearDown() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Employee employee = em.find(Employee.class, employeeId);

        em.remove(employee);
        em.remove(employee.getPhones().iterator().next());

        transaction.commit();
    }

    @Test
    public void findEmployee() {
        Employee employee = em.find(Employee.class, employeeId);

        assertTrue(employee.getPhones().size() > 0);
    }

}
