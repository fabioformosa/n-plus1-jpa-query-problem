package it.fabioformosa.jpafetchstudy;

import it.fabioformosa.jpafetchstudy.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.services.CompanyService;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class CompanyServiceIntegrationTest extends AbstractPostgresqlContainerTestSuite{

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void given10Companies_whenTheFirstPageOfIsFetched_thenTheQueryCounterShouldBe2(){
        PaginatedListDto<CompanyDto> companyDtoList = companyService.list(0, 5);

        Assertions.assertThat(companyDtoList.getTotalItems()).isEqualTo(10);
        Assertions.assertThat(companyDtoList.getItems()).hasSize(5);
        Assertions.assertThat(companyDtoList.getTotalPages()).isEqualTo(2);

        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(2);
    }

}
