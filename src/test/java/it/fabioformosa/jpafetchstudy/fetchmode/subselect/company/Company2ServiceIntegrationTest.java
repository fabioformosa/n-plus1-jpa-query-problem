package it.fabioformosa.jpafetchstudy.fetchmode.subselect.company;

import it.fabioformosa.jpafetchstudy.AbstractIntegrationTestSuite;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.services.Company2Service;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * The OneToMany association is affected by the n+1 query problem if we have a list of entities (companies) and for each of them
 * we access to the nested collection (employees). This kind of pattern is common, supposing the need to convert the entities in DTOs.
 * An extra query must be performed for each iteration, resulting in the n+1 query problem.
 *
 * The solution is to specify a join fetch whenever we know we're going to access to the nested collection
 */
public class Company2ServiceIntegrationTest extends AbstractIntegrationTestSuite {

    @Autowired
    private Company2Service companyService;

    @Autowired
    private EntityManager entityManager;

    /**
     * By default, the OneToMany association is defined with a fetchType=Lazy.
     * In the service we iterate over the companies and we access to the related nested collection of employees
     * to convert into DTOs.
     * If we forget to specify explicitly a fetch join in the query, an extra query is done for each company
     * to load the associated employees. In case of FethMode = Subselect, the extra query is done through a subselect
     */
    @Test
    void givenCompaniesWithAssociationEmployees_whenTheFetchTypeIsLazy_thenTheNPlus1QueryProblemIsPresent(){
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        int pageSize = 5;
        int totalNumOfCompanies = 1000;

        PaginatedListDto<CompanyDto> companyDtoList = companyService.list(0, pageSize);

        Assertions.assertThat(companyDtoList.getTotalItems()).isEqualTo(totalNumOfCompanies);
        Assertions.assertThat(companyDtoList.getItems()).hasSize(pageSize);
        Assertions.assertThat(companyDtoList.getTotalPages()).isEqualTo(totalNumOfCompanies / pageSize);


        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);
        Assertions.assertThat(statistics.getPrepareStatementCount()).isEqualTo(8);

        // OK: n+1 query problem not present: the only 1 expected fetch is for the sub-select
        // but in case of pagination, there is a performance issue in the subquery
        Assertions.assertThat(statistics.getCollectionFetchCount()).isEqualTo(1);
    }

    /**
     * Specifying a join fetch into the query, the problem explained above is solved!
     */
    @Test
    void givenCompaniesWithAssociationEmployees_whenTheQueryFetchesExplicitlyViaJQL_thenTheNPlus1QueryProblemIsNotPresent(){
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        int pageSize = 5;
        int totalNumOfCompanies = 1000;
        PaginatedListDto<CompanyDto> companyDtoList = companyService.listWithFetchViaJQL(0, pageSize);

        Assertions.assertThat(companyDtoList.getTotalItems()).isEqualTo(totalNumOfCompanies);
        Assertions.assertThat(companyDtoList.getItems()).hasSize(pageSize);
        Assertions.assertThat(companyDtoList.getTotalPages()).isEqualTo(totalNumOfCompanies / pageSize);

        // OK: n+1 query problem not present
        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);
        Assertions.assertThat(statistics.getPrepareStatementCount()).isEqualTo(2);
        Assertions.assertThat(statistics.getCollectionFetchCount()).isEqualTo(0);
    }

}
