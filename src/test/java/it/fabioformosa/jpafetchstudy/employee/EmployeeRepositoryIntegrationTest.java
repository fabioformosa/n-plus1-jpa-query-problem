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

    @Test
    void given1000Employees_whenTheFirstPageOfIsFetched_thenTheQueryCounterShouldBe2() {
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        int pageSize = 5;
        Page<Employee> paginatedEmployeeList = employeeRepository.findAll(PageRequest.of(0, pageSize, Sort.by("id")));
        Assertions.assertThat(paginatedEmployeeList.getTotalElements()).isEqualTo(1000);
        Assertions.assertThat(paginatedEmployeeList.getContent()).hasSize(pageSize);
        Assertions.assertThat(paginatedEmployeeList.getTotalPages()).isEqualTo(200);

        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);
        Assertions.assertThat(statistics.getCollectionFetchCount()).isEqualTo(0);
    }
}
