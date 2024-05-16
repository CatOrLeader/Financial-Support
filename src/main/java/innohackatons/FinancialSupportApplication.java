package innohackatons;

import innohackatons.configuration.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class FinancialSupportApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinancialSupportApplication.class, args);
    }
}
