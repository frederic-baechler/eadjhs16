package hr;

import static org.junit.Assert.*;
import org.junit.Test;

public class RelationshipTest extends AbstractTest {

    @Test
    public void findEmployee() {
        Employee employee = em.find(Employee.class, employeeId);

        assertTrue(employee.getPhones().size() > 0);
    }

    @Test
    public void persistAfterRemove() {
        Employee employee = new Employee();
        em.persist(employee);
        assertTrue(em.contains(employee));

        em.remove(employee);
        assertFalse(em.contains(employee));

        em.persist(employee);
        assertTrue(em.contains(employee));
    }

}
