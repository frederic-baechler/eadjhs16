package ch.bfh.sws.eadj;

import ch.bfh.sws.eadj.hr.Employee;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class HrTest extends AbstractHrTest {

    @Test
    public void findEmployee() {
        Employee employee = em.find(Employee.class, employeeId);

        employee.getAddress();

        assertNotNull(employee);
    }

}
