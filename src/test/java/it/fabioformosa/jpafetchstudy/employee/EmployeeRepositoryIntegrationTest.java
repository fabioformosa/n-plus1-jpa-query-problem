package it.fabioformosa.jpafetchstudy.employee;

import it.fabioformosa.jpafetchstudy.AbstractIntegrationTestSuite;
import it.fabioformosa.jpafetchstudy.entitites.Employee;
import it.fabioformosa.jpafetchstudy.repositories.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;

public class EmployeeRepositoryIntegrationTest extends AbstractIntegrationTestSuite {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * By default the ManyToOne association has defined with a fetchType=Eager
     * If the query doesn't specify explicitely a fetch, the associated entity is fetched with an extra query for each
     * element of the returned colletion
     */
    @Test
    void given1000EmployeesWithAssociatedCompanies_whenTheFetchTypeIsEager_thenTheNPlus1QueryProblemIsPresent() {
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        int pageSize = 5;
        Page<Employee> paginatedEmployeeList = employeeRepository.findAll(PageRequest.of(0, pageSize, Sort.by("id")));
        Assertions.assertThat(paginatedEmployeeList.getTotalElements()).isEqualTo(1000);
        Assertions.assertThat(paginatedEmployeeList.getContent()).hasSize(pageSize);
        Assertions.assertThat(paginatedEmployeeList.getTotalPages()).isEqualTo(200);

        //Assert the association is eager
        Assertions.assertThat(paginatedEmployeeList.getContent().get(0).getCompany()).isNotNull();

        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);

        // !!! n+1 query problem !!!
        Assertions.assertThat(statistics.getEntityFetchCount()).isEqualTo(pageSize);
    }
}
