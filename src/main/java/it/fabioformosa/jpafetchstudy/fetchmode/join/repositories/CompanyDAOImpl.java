package it.fabioformosa.jpafetchstudy.fetchmode.join.repositories;

import it.fabioformosa.jpafetchstudy.fetchmode.join.entitites.Company;
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
public class CompanyDAOImpl implements CompanyDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<Company> listCompanyWithEmployeesViaCriteria(int pageNum, int pageSize){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> root = criteriaQuery.from(Company.class);
        root.fetch("employees");
        criteriaQuery.orderBy(new OrderImpl(root.get("id")));

        TypedQuery<Company> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(pageSize);
        query.setFirstResult(pageNum * pageSize);
        List<Company> companies = query.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.from(Company.class);
        countQuery.select(criteriaBuilder.count(root));
        long totalCompanies = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(companies, PageRequest.of(pageNum, pageSize), totalCompanies);

    }

}
