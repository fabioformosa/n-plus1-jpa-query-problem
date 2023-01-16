package it.fabioformosa.jpafetchstudy.company;

import it.fabioformosa.jpafetchstudy.AbstractIntegrationTestSuite;
import it.fabioformosa.jpafetchstudy.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.services.CompanyService;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class CompanyServiceIntegrationTest extends AbstractIntegrationTestSuite {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EntityManager entityManager;

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

}
