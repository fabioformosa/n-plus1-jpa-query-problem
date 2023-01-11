package it.fabioformosa.jpafetchstudy;

import it.fabioformosa.jpafetchstudy.entitites.Company;
import it.fabioformosa.jpafetchstudy.repositories.CompanyRepository;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;

@SpringBootTest
class JpaFetchStudyApplicationTests extends AbstractPostgresqlContainerTestSuite{

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void given10Companies_whenAPageOf5IsFetched_thenTheQueryCounterShouldBe2() {
        //1 Wisoky and Sons => 102 employees
        //2 Larkin and Sons => 89 employees
        //3 Ondricka-Yundt => 93 employees

        Page<Company> companyPage = companyRepository.findAll(PageRequest.of(0, 5, Sort.by("id")));
        Assertions.assertThat(companyPage.getTotalElements()).isEqualTo(10);
        Assertions.assertThat(companyPage.getContent()).hasSize(5);
        Assertions.assertThat(companyPage.getTotalPages()).isEqualTo(2);

        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);
    }

}
