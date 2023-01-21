package it.fabioformosa.jpafetchstudy.fetchmode.join.repositories;

import it.fabioformosa.jpafetchstudy.fetchmode.join.entitites.Employee;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<Employee> listEmployeesWithCompanyByCriteriaBuilder(int pageNum, int pageSize){
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        Root<Employee> root = cq.from(Employee.class);
        cq.select(qb.count(root));
        long totalEmployees = entityManager.createQuery(cq).getSingleResult();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        criteriaQuery.from(Employee.class).fetch("company");
        criteriaQuery.orderBy(new OrderImpl(root.get("id")));
        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(pageSize);
        query.setFirstResult(pageSize * pageNum);
        List<Employee> employees = query.getResultList();

        return new PageImpl<>(employees, PageRequest.of(pageNum, pageSize), totalEmployees);
    }

}
