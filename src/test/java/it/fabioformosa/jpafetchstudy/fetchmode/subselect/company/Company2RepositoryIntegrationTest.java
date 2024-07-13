package it.fabioformosa.jpafetchstudy.fetchmode.subselect.company;

import it.fabioformosa.jpafetchstudy.AbstractIntegrationTestSuite;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.entities.Company2;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.repositories.Company2Repository;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;

/**
 * The OneToMany association is affected by the n+1 query problem if we have a list of entities (companies) and for each of them
 * we access to the nested collection (employees). This kind of pattern is common, supposing the need to convert the entities in DTOs.
 * An extra query must be performed for each iteration, resulting in the n+1 query problem.
 */
class Company2RepositoryIntegrationTest extends AbstractIntegrationTestSuite {

    @Autowired
    private Company2Repository companyRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * By default, the OneToMany association is defined with FetchType=Lazy
     * If we don't access to the nested collection, no extra queries are performed
     */
    @Test
    void givenCompaniesWithAssociatedEmployees_whenTheFetchTypeIsLazy_thenTheQueryCounterShouldBe2() {
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        Page<Company2> companyPage = companyRepository.findAll(PageRequest.of(0, 5, Sort.by("id")));
        Assertions.assertThat(companyPage.getTotalElements()).isEqualTo(1000);
        Assertions.assertThat(companyPage.getContent()).hasSize(5);
        Assertions.assertThat(companyPage.getTotalPages()).isEqualTo(200);

        // OK: n+1 query problem not present
        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);
        Assertions.assertThat(statistics.getCollectionFetchCount()).isEqualTo(0);
    }

}
