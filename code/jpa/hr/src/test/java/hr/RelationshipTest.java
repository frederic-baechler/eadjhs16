package hr;

import static org.junit.Assert.*;
import org.junit.Test;

public class RelationshipTest extends AbstractTest {

    @Test
    public void findEmployee() {
        Employee employee = em.find(Employee.class, employeeId);

        assertTrue(employee.getPhones().size() > 0);
    }

}
