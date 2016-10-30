package ch.bfh.sws.eadj;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class HrTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

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

    @Test
    public void createEmployee() {
        assertTrue(true);
    }

}
