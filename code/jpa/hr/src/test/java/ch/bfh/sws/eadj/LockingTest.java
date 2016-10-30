package ch.bfh.sws.eadj;

import ch.bfh.sws.eadj.hr.DesignProject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.Test;

public class LockingTest {

    @Test
    public void lostUpdate() throws InterruptedException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hr");
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        DesignProject designProject = new DesignProject();
        em.persist(designProject);
        transaction.commit();

        em.clear();
        emf.getCache().evictAll();

        Updater u1 = new Updater(designProject.getId(), 1000);
        Updater u2 = new Updater(designProject.getId(), 0);

        u1.start();
        u2.start();

        u1.join();
        u2.join();
    }

    public class Updater extends Thread {

        private final Integer id;
        private final int sleepTime;

        public Updater(Integer id, int sleepTime) {
            this.id = id;
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            try {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("hr");
                EntityManager em = emf.createEntityManager();

                EntityTransaction transaction = em.getTransaction();
                transaction.begin();

                DesignProject designProject = em.find(DesignProject.class, id);
                designProject.setName("ABC");

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ex) {
                }

                transaction.commit();

            } catch (Exception e) {
                Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }
}
