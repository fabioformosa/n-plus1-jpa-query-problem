package it.fabioformosa.jpafetchstudy;

import it.fabioformosa.jpafetchstudy.services.CompanyService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Test
    void test(){
        Assertions.assertThat(companyService).isNotNull();
    }

}
