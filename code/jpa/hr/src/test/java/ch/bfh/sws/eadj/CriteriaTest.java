package ch.bfh.sws.eadj;

import ch.bfh.sws.eadj.hr.Department;
import ch.bfh.sws.eadj.hr.Department_;
import ch.bfh.sws.eadj.hr.Employee;
import ch.bfh.sws.eadj.hr.Employee_;
import ch.bfh.sws.eadj.hr.Phone;
import ch.bfh.sws.eadj.hr.PhoneType;
import ch.bfh.sws.eadj.hr.Phone_;
import ch.bfh.sws.eadj.hr.Project;
import ch.bfh.sws.eadj.hr.Project_;
import ch.bfh.sws.eadj.hr.to.EmployeeStats;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CriteriaTest extends AbstractHrTest {

    private static final String NAME = "name";
    private static final String SALARY = "salary";

    @Test
    public void queryNameAndSalary() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> queryNameAndSalary <<<<<<<<<<<<<<<<<<<<");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

        Root<Employee> employee = cq.from(Employee.class);

        Parameter<String> paramName = cb.parameter(String.class, NAME);
        Parameter<Long> paramSalary = cb.parameter(Long.class, SALARY);

        cq.where(
                cb.and(
                        cb.equal(employee.get(Employee_.name), paramName),
                        cb.equal(employee.get(Employee_.salary), paramSalary))
        );

        cq.select(employee);

        TypedQuery<Employee> q = em.createQuery(cq);
        q.setParameter(NAME, "Roger Federer");
        q.setParameter(SALARY, 2000000);

        Employee federer = q.getSingleResult();

        Assert.assertNotNull(federer);
    }

    @Test(expected = NoResultException.class)
    public void noResult() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> namedQueryException <<<<<<<<<<<<<<<<<<<<");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

        Root<Employee> employee = cq.from(Employee.class);

        Parameter<String> param = cb.parameter(String.class, NAME);
        cq.where(cb.equal(employee.get(Employee_.name), param));

        cq.select(employee);

        TypedQuery<Employee> q = em.createQuery(cq);
        q.setParameter(NAME, "Gelson Fernandes");

        q.getSingleResult();

        Assert.fail();
    }

    @Test
    public void queryAgregate() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> queryAgregate <<<<<<<<<<<<<<<<<<<<");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Employee> employee = cq.from(Employee.class);

        cq.multiselect(cb.count(employee));

        TypedQuery<Long> q = em.createQuery(cq);

        Long result = q.getSingleResult();
        LOGGER.log(Level.INFO, "COUNT(e): {0}", result);

        Assert.assertNotNull(result);

    }

    @Test
    public void queryProjection() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> queryProjection <<<<<<<<<<<<<<<<<<<<");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<Employee> employee = cq.from(Employee.class);

        cq.multiselect(employee.get(Employee_.name), employee.get(Employee_.salary));

        Query q = em.createQuery(cq);

        List<Object[]> objs = q.getResultList();

        Assert.assertNotNull(objs);
        Assert.assertTrue(objs.size() > 0);

        for (Object[] objects : objs) {
            LOGGER.log(Level.INFO, "Emp: {0} : {1}", new Object[]{objects[0], objects[1]});
        }
    }

    @Test
    public void queryConstructorExpression() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> queryConstructorExpression <<<<<<<<<<<<<<<<<<<<");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EmployeeStats> cq = cb.createQuery(EmployeeStats.class);

        Root<Employee> employee = cq.from(Employee.class);

        cq.multiselect(
                employee.get(Employee_.name),
                employee.get(Employee_.salary),
                employee.get(Employee_.address)
        );

        Query q = em.createQuery(cq);

        List<EmployeeStats> emps = q.getResultList();

        Assert.assertNotNull(emps);
        Assert.assertTrue(emps.size() > 0);

        for (EmployeeStats employeeStats : emps) {
            LOGGER.log(Level.INFO, "EmployeeStats: {0} : {1} : {2}", new Object[]{employeeStats.getName(), employeeStats.getSalary(), employeeStats.getAddress()});
            if (employeeStats.getAddress() != null) {
                LOGGER.log(Level.INFO, "{0}Verwaltet? : ", em.contains(employeeStats.getAddress()));
            }
        }

    }

    @Test
    public void queryJoin() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> queryJoin <<<<<<<<<<<<<<<<<<<<");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);

        Root<Employee> employee = cq.from(Employee.class);

        ListJoin<Employee, Project> project = employee.join(Employee_.projects);

        Parameter<String> param = cb.parameter(String.class, NAME);
        cq.where(cb.equal(project.get(Project_.name), param));

        cq.select(project);

        TypedQuery<Project> q = em.createQuery(cq);
        q.setParameter(NAME, "Arcos");

        List<Project> ps = q.getResultList();

        Assert.assertNotNull(ps);
        Assert.assertTrue(ps.size() > 0);

        for (Project p : ps) {
            LOGGER.info(p.toString());
        }
    }

    @Test
    public void inParam() throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Phone> cq = cb.createQuery(Phone.class);

        Root<Phone> p = cq.from(Phone.class);
        cq.where(cb.in(p.get(Phone_.type)).value(PhoneType.HOME).value(PhoneType.WORK));

        cq.select(p);

        TypedQuery<Phone> q = em.createQuery(cq);

        List<Phone> ps = q.getResultList();

        Assert.assertNotNull(ps);
        Assert.assertTrue(ps.size() > 0);
    }

    @Test
    public void havingCount() throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> havingCount <<<<<<<<<<<<<<<<<<<<");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();

        ParameterExpression<Long> param = cb.parameter(Long.class, "count");

        Root<Employee> employee = cq.from(Employee.class);
        ListJoin<Employee, Project> projects = employee.join(Employee_.projects);

        cq.groupBy(employee.get(Employee_.name));
        cq.having(cb.gt(cb.count(projects), param));

        cq.multiselect(employee.get(Employee_.name), cb.count(projects));

        Query q = em.createQuery(cq);
        q.setParameter("count", 0);

        List<Object[]> result = q.getResultList();
        Assert.assertNotNull(result);

        for (Object[] o : result) {
            LOGGER.log(Level.INFO, "{0} {1}", new Object[]{o[0], o[1]});
        }
    }

    @Test
    public void testSearchEmployees() {
        LOGGER.info(">>>>>>>>>>>>>>>>>>> testSearchEmployees <<<<<<<<<<<<<<<<<<<<");
        
        List<Employee> list = searchEmployees("Peter Muster", null, "Arcos");

        assertFalse(list.isEmpty());
        assertTrue(list.size() == 1);
        
        list = searchEmployeesWithConjunction("Peter Muster", null, "Arcos");

        assertFalse(list.isEmpty());
        assertTrue(list.size() == 1);
    }

    private List<Employee> searchEmployees(String employeeName, String departmentName, String projectName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

        Root<Employee> employee = cq.from(Employee.class);
        Root<Department> department = cq.from(Department.class);
        Root<Project> project = cq.from(Project.class);
        cq.select(employee);

        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotEmpty(employeeName)) {
            ParameterExpression<String> parameter = cb.parameter(String.class, "employeeName");
            predicates.add(cb.equal(employee.get(Employee_.name), parameter));
        }
        if (StringUtils.isNotEmpty(departmentName)) {
            ParameterExpression<String> parameter = cb.parameter(String.class, "departmentName");
            predicates.add(cb.equal(department.get(Department_.name), parameter));
        }
        if (StringUtils.isNotEmpty(projectName)) {
            ParameterExpression<String> parameter = cb.parameter(String.class, "projectName");
            predicates.add(cb.equal(project.get(Project_.name), parameter));
        }
        
        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Employee> q = em.createQuery(cq);
        if (StringUtils.isNotEmpty(employeeName)) {
            q.setParameter("employeeName", employeeName);
        }
        if (StringUtils.isNotEmpty(departmentName)) {
            q.setParameter("departmentName", departmentName);
        }
        if (StringUtils.isNotEmpty(projectName)) {
            q.setParameter("projectName", projectName);
        }
        return q.getResultList();
    }

    private List<Employee> searchEmployeesWithConjunction(String employeeName, String departmentName, String projectName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

        Root<Employee> employee = cq.from(Employee.class);
        Root<Department> department = cq.from(Department.class);
        Root<Project> project = cq.from(Project.class);
        cq.select(employee);

        Predicate where = cb.conjunction();
        if (StringUtils.isNotEmpty(employeeName)) {
            ParameterExpression<String> parameter = cb.parameter(String.class, "employeeName");
            where = cb.and(where, cb.equal(employee.get(Employee_.name), parameter));
        }
        if (StringUtils.isNotEmpty(departmentName)) {
            ParameterExpression<String> parameter = cb.parameter(String.class, "departmentName");
            where = cb.and(where, cb.equal(department.get(Department_.name), parameter));
        }
        if (StringUtils.isNotEmpty(projectName)) {
            ParameterExpression<String> parameter = cb.parameter(String.class, "projectName");
            where = cb.and(where, cb.equal(project.get(Project_.name), parameter));
        }
        
        cq.where(where);

        TypedQuery<Employee> q = em.createQuery(cq);
        if (StringUtils.isNotEmpty(employeeName)) {
            q.setParameter("employeeName", employeeName);
        }
        if (StringUtils.isNotEmpty(departmentName)) {
            q.setParameter("departmentName", departmentName);
        }
        if (StringUtils.isNotEmpty(projectName)) {
            q.setParameter("projectName", projectName);
        }
        return q.getResultList();
    }
}
