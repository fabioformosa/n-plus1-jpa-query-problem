package it.fabioformosa.jpafetchstudy.fetchmode.join.company;

import it.fabioformosa.jpafetchstudy.AbstractIntegrationTestSuite;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.fetchmode.join.services.CompanyService;
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
public class CompanyServiceIntegrationTest extends AbstractIntegrationTestSuite {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EntityManager entityManager;

    /**
     * By default, the OneToMany association is defined with a fetchType=Lazy.
     * In the service we iterate over the companies and we access to the related nested collection of employees
     * to convert into DTOs.
     * If we forget to specify explicitly a fetch join in the query, an extra query is done for each company
     * to load the associated employees
     */
    @Test
    void givenCompaniesWithAssociationEmployees_whenTheFetchTypeIsLazy_thenTheNPlus1QueryProblemIsPresent(){
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        int pageSize = 5;
        PaginatedListDto<CompanyDto> companyDtoList = companyService.list(0, pageSize);

        Assertions.assertThat(companyDtoList.getTotalItems()).isEqualTo(10);
        Assertions.assertThat(companyDtoList.getItems()).hasSize(pageSize);
        Assertions.assertThat(companyDtoList.getTotalPages()).isEqualTo(2);


        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);

        // !!! n+1 query problem !!!
        Assertions.assertThat(statistics.getCollectionFetchCount()).isEqualTo(pageSize);
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
        PaginatedListDto<CompanyDto> companyDtoList = companyService.listWithFetchViaJQL(0, pageSize);

        Assertions.assertThat(companyDtoList.getTotalItems()).isEqualTo(10);
        Assertions.assertThat(companyDtoList.getItems()).hasSize(pageSize);
        Assertions.assertThat(companyDtoList.getTotalPages()).isEqualTo(2);

        // OK: n+1 query problem not present
        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);
        Assertions.assertThat(statistics.getCollectionFetchCount()).isEqualTo(0);
    }

    /**
     * Specifying a join fetch into the query, the problem explained above is solved!
     */
    @Test
    void givenCompaniesWithAssociationEmployees_whenTheQueryFetchesExplicitlyViaSpecification_thenTheNPlus1QueryProblemIsNotPresent(){
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        int pageSize = 5;
        PaginatedListDto<CompanyDto> companyDtoList = companyService.listWithFetchViaSpecification(0, pageSize);

        Assertions.assertThat(companyDtoList.getTotalItems()).isEqualTo(10);
        Assertions.assertThat(companyDtoList.getItems()).hasSize(pageSize);
        Assertions.assertThat(companyDtoList.getTotalPages()).isEqualTo(2);

        // OK: n+1 query problem not present
        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);
        Assertions.assertThat(statistics.getCollectionFetchCount()).isEqualTo(0);
    }

    /**
     * Specifying a join fetch into the query, the problem explained above is solved!
     */
    @Test
    void givenCompaniesWithAssociationEmployees_whenTheQueryFetchesExplicitlyViaCriteriaBuilder_thenTheNPlus1QueryProblemIsNotPresent(){
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        int pageSize = 5;
        PaginatedListDto<CompanyDto> companyDtoList = companyService.listWithFetchViaCriteriaBuilder(0, pageSize);

        Assertions.assertThat(companyDtoList.getTotalItems()).isEqualTo(10);
        Assertions.assertThat(companyDtoList.getItems()).hasSize(pageSize);
        Assertions.assertThat(companyDtoList.getTotalPages()).isEqualTo(2);

        // OK: n+1 query problem not present
        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);
        Assertions.assertThat(statistics.getCollectionFetchCount()).isEqualTo(0);
    }


    /**
     * Specifying a join fetch into the query, the problem explained above is solved!
     */
    @Test
    void givenCompaniesWithAssociationEmployees_whenTheQueryFetchesExplicitlyViaEntityGraph_thenTheNPlus1QueryProblemIsNotPresent(){
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        int pageSize = 5;
        PaginatedListDto<CompanyDto> companyDtoList = companyService.listWithFetchViaEntityGraph(0, pageSize);

        Assertions.assertThat(companyDtoList.getTotalItems()).isEqualTo(10);
        Assertions.assertThat(companyDtoList.getItems()).hasSize(pageSize);
        Assertions.assertThat(companyDtoList.getTotalPages()).isEqualTo(2);

        // OK: n+1 query problem not present
        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);
        Assertions.assertThat(statistics.getCollectionFetchCount()).isEqualTo(0);
    }

}
