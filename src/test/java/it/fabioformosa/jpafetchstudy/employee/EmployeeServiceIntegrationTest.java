package it.fabioformosa.jpafetchstudy.employee;

import it.fabioformosa.jpafetchstudy.AbstractIntegrationTestSuite;
import it.fabioformosa.jpafetchstudy.dtos.EmployeeDto;
import it.fabioformosa.jpafetchstudy.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.services.EmployeeService;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

class EmployeeServiceIntegrationTest extends AbstractIntegrationTestSuite {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EntityManager entityManager;

    /**
     * By default the ManyToOne association has defined with a fetchType=Eager
     * If the query doesn't specify explicitely a fetch, the associated entity is fetched with an extra query for each
     * element of the returned colletion
     */
    @Test
    void given1000EmployeesWithAssociatedCompanies_whenTheFetchTypeIsEager_thenNPlus1ProblemIsPresent(){
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        int pageSize = 5;
        PaginatedListDto<EmployeeDto> employeePage = employeeService.list(0, pageSize);
        Assertions.assertThat(employeePage.getTotalItems()).isEqualTo(1000);
        Assertions.assertThat(employeePage.getItems()).hasSize(5);
        Assertions.assertThat(employeePage.getTotalPages()).isEqualTo(200);

        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);

        // !!! n+1 query problem !!!
        Assertions.assertThat(statistics.getEntityFetchCount()).isEqualTo(pageSize);
    }

}