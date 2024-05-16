package innohackatons;

import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public abstract class IntegrationEnvironment {
    public static final PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("finances")
            .withUsername("postgres")
            .withPassword("postgres");
        POSTGRES.start();

        runMigration(POSTGRES);
    }

    private static void runMigration(@NotNull JdbcDatabaseContainer<?> container) {
        try (Connection connection = DriverManager.getConnection(
            container.getJdbcUrl(),
            container.getUsername(),
            container.getPassword()
        )) {
            Database database =
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            var dir = new File("").toPath().toAbsolutePath().resolve(Path.of("db"));
            System.out.println(dir);
            Liquibase liquibase = new Liquibase(
                Path.of("migrations", "db.master-changelog.xml").toString(),
                new DirectoryResourceAccessor(dir),
                database
            );

            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | FileNotFoundException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    private static void dynamicPropertyConfiguration(DynamicPropertyRegistry registry) {
        registry.add("spring.autoconfigure.exclude", () -> "");

        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}

class HealthcheckTest extends IntegrationEnvironment {
    @Test
    void healthcheck() {
        assertThat(POSTGRES.isRunning()).isTrue();
    }
}
