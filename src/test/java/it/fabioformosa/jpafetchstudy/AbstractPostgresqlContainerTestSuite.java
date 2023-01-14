package it.fabioformosa.jpafetchstudy;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ContextConfiguration(initializers = {AbstractPostgresqlContainerTestSuite.Initializer.class})
public class AbstractPostgresqlContainerTestSuite {
    private static final String POSTGRESQL_DOCKER_IMAGE = "postgres:14.5";

    private static final PostgreSQLContainer POSTGRESQL_CONTAINER =
            (PostgreSQLContainer) new PostgreSQLContainer(DockerImageName.parse(POSTGRESQL_DOCKER_IMAGE))
                    .withInitScript("db/postgres-init.sql");

    static {
        POSTGRESQL_CONTAINER.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            initPostgresqlContainer(configurableApplicationContext);
        }

        private void initPostgresqlContainer(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.driver-class-name=" + POSTGRESQL_CONTAINER.getDriverClassName(),
                    "spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
                    "spring.datasource.password=" + POSTGRESQL_CONTAINER.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
