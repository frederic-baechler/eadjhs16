package hr;

import hr.to.EmployeeStats;
import hr.to.EmployeeTO;
import java.util.logging.Level;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;
import org.qlrm.mapper.JpaResultMapper;

public class QueryTest extends AbstractTest {

    @Test
    public void namedQuery() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> namedQuery <<<<<<<<<<<<<<<<<<<<");

        Query q = em.createNamedQuery(Employee.FIND_BY_NAME);
        q.setParameter("name", "Roger Federer");

        Employee federer = (Employee) q.getSingleResult();

        Assert.assertNotNull(federer);
    }

    @Test
    public void namedQueryInXML() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> namedQuery <<<<<<<<<<<<<<<<<<<<");

        Query q = em.createNamedQuery("Employee.findByDepartmentName");
        q.setParameter("departmentName", "PR");

        Employee federer = (Employee) q.getSingleResult();

        Assert.assertNotNull(federer);
    }

    @Test(expected = NoResultException.class)
    public void noResult() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> noResult <<<<<<<<<<<<<<<<<<<<");

        Query q = em.createNamedQuery(Employee.FIND_BY_NAME);
        q.setParameter("name", "Gelson Fernandes");

        q.getSingleResult();

        Assert.fail();
    }

    @Test
    public void inheritance() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> noResult <<<<<<<<<<<<<<<<<<<<");

        TypedQuery<Project> q = em.createQuery("select p from Project p", Project.class);
        List<Project> list = q.getResultList();
        Assert.assertTrue(!list.isEmpty());
    }

    @Test(expected = NonUniqueResultException.class)
    public void tooManyResults() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> tooManyResults <<<<<<<<<<<<<<<<<<<<");

        TypedQuery<Employee> q = em.createQuery("select e from Employee e", Employee.class);

        q.getSingleResult();

        Assert.fail();
    }

    @Test
    public void queryAgregate() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> queryAgregate <<<<<<<<<<<<<<<<<<<<");

        // Aggregate
        String queryString = "select count(e) from Employee e";
        LOGGER.info(queryString);
        Query query = em.createQuery(queryString);

        Long result = (Long) query.getSingleResult();
        LOGGER.log(Level.INFO, "COUNT(e): {0}", result);

        Assert.assertNotNull(result);

    }

    @Test
    public void queryProjection() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> queryProjection <<<<<<<<<<<<<<<<<<<<");

        // Projektion
        String queryString = "select e.name, e.salary from Employee e";
        LOGGER.info(queryString);
        Query query = em.createQuery(queryString);
        List<Object[]> objs = query.getResultList();

        Assert.assertNotNull(objs);
        Assert.assertTrue(objs.size() > 0);

        for (Object[] objects : objs) {
            LOGGER.log(Level.INFO, "Emp: {0} : {1}", new Object[]{objects[0], objects[1]});
        }
    }

    @Test
    public void constructorExpressionJql() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> queryConstructorExpression <<<<<<<<<<<<<<<<<<<<");

        LOGGER.info(EmployeeStats.SELECT_NEW);

        Query query = em.createQuery(EmployeeStats.SELECT_NEW);
        List<EmployeeStats> emps = query.getResultList();

        Assert.assertNotNull(emps);
        Assert.assertTrue(emps.size() > 0);

        for (EmployeeStats employeeStats : emps) {
            LOGGER.log(Level.INFO, "EmployeeStats: {0} : {1} : {2}",
                    new Object[]{employeeStats.getName(), employeeStats.getSalary(), employeeStats.getAddress()});
            if (employeeStats.getAddress() != null) {
                LOGGER.log(Level.INFO, "Verwaltet? : {0}", em.contains(employeeStats.getAddress()));
            }
        }
    }

    @Test
    public void queryJoin() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> queryJoin <<<<<<<<<<<<<<<<<<<<");

        String queryString = "select e from Employee e join e.phones p";
        LOGGER.info(queryString);
        Query query = em.createQuery(queryString);
        List<Employee> emps = query.getResultList();

        Assert.assertNotNull(emps);
        Assert.assertTrue(emps.size() > 0);

        for (Employee employee : emps) {
            LOGGER.info(employee.toString());
            LOGGER.info(employee.getPhones().toString());
        }
    }

    @Test
    public void queryNative() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> queryNative <<<<<<<<<<<<<<<<<<<<");

        String queryString = "SELECT * FROM EMPLOYEE";
        LOGGER.log(Level.INFO, "Native: {0}", queryString);
        Query query = em.createNativeQuery(queryString, Employee.class);
        List<Employee> emps = query.getResultList();

        Assert.assertNotNull(emps);
        Assert.assertTrue(emps.size() > 0);

        for (Employee employee : emps) {
            LOGGER.info(employee.toString());
        }
    }

    @Test
    public void twoEntitesAsResult() {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> queryToMany <<<<<<<<<<<<<<<<<<<<");

        String queryString = "select e, p from Employee e join e.projects p";
        Query query = em.createQuery(queryString);
        List<Object[]> result = query.getResultList();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() > 0);

        for (Object[] objs : result) {
        }
    }

    @Test
    public void avgQuery() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> avgQuery <<<<<<<<<<<<<<<<<<<<");

        Query q = em.createNamedQuery(Employee.AVG);

        Double avg = (Double) q.getSingleResult();

        Assert.assertNotNull(avg);
    }

    @Test
    public void memberOf() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> memberOf <<<<<<<<<<<<<<<<<<<<");

        Employee e = em.find(Employee.class, employeeId);

        String queryString = "select e.name, count(d) from Employee e join e.directs d where :employee member of e.directs group by e.name";
        Query query = em.createQuery(queryString);
        query.setParameter("employee", e);

        List<Object[]> list = query.getResultList();

        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);

        for (Object[] o : list) {
            LOGGER.log(Level.INFO, "{0} {1}", new Object[]{o[0], o[1]});
        }
    }

    @Test
    public void constructorExpressionNative() {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> constructorExpressionNative <<<<<<<<<<<<<<<<<<<<");

        Query q = em.createNativeQuery("SELECT ID, NAME FROM EMPLOYEE", "EmployeeTO");
        List<EmployeeTO> list = q.getResultList();

        for (EmployeeTO eil : list) {
            System.out.println(eil);
        }
    }

    @Test
    public void constructorExpressionQlrm() {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> constructorExpressionQlrm <<<<<<<<<<<<<<<<<<<<");

        Query q = em.createNativeQuery("SELECT ID, NAME FROM EMPLOYEE");

        JpaResultMapper jrm = new JpaResultMapper();
        List<EmployeeTO> list = jrm.list(q, EmployeeTO.class);

        for (EmployeeTO eil : list) {
            System.out.println(eil);
        }
    }

    @Test
    public void entityGraph() {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> entityGraph <<<<<<<<<<<<<<<<<<<<");

        em.clear();
        emf.getCache().evictAll();

        EntityGraph includePhones = em.getEntityGraph("includePhones");

        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.loadgraph", includePhones);

        Employee employee = em.find(Employee.class, employeeId, props);

        Assert.assertNotNull(employee);
    }
}
