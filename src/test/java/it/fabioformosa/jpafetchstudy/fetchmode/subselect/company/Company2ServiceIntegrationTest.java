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


        //1 query for counting the total and another query to fetch the first page
        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);

        // OK: n+1 query problem not present. The whole collection of employees is fetched with one only extra query using the sub-select statement
        Assertions.assertThat(statistics.getCollectionFetchCount()).isEqualTo(1);

        //Nonetheless The subquery comes with a performance issue (yet another n+1 query problem). The subquery fetches the entire table of employees
        //not only employees linked to the first page of companies. For each employee, hibernate retrieve its company with an extra fetch query,
        // since the ManyToOne relation with the Company
        // So it results in hundreds of entity fetched! (in this example, 622 fetches)
        Assertions.assertThat(statistics.getEntityFetchCount()).isGreaterThan(1);
    }

    /**
     * Specifying a join fetch into the query, the problem explained above is solved because we fetches only the employees linked
     * to the first page of company
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
        Assertions.assertThat(statistics.getCollectionFetchCount()).isZero();
        Assertions.assertThat(statistics.getEntityFetchCount()).isZero();
    }

}
