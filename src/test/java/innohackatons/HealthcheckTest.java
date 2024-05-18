package innohackatons;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class HealthcheckTest extends IntegrationEnvironment {
    @Test
    void healthcheck() {
        assertThat(POSTGRES.isRunning()).isTrue();
    }
}
