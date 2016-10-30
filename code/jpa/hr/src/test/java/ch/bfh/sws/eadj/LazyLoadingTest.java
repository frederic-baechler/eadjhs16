package ch.bfh.sws.eadj;

import static ch.bfh.sws.eadj.AbstractHrTest.em;
import ch.bfh.sws.eadj.hr.Employee;
import javax.persistence.Query;
import org.junit.Test;

public class LazyLoadingTest extends AbstractHrTest {

    @Test
    public void lazyLoading() throws Exception {
        Query q = em.createNamedQuery(Employee.FIND_BY_NAME);
        q.setParameter("name", "Roger Federer");

        Employee federer = (Employee) q.getSingleResult();
        
        em.clear();
        
        federer.getPhones().iterator();
    }

}
