package innohackatons.repository;

import innohackatons.IntegrationEnvironment;
import innohackatons.entity.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
public class UserRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    public void assertThatAddUserWorksCorrectly() {
        User user = new User()
            .setName("Test User");

        user = userRepository.save(user);

        assertThat(userRepository.findById(user.getId())).isPresent();
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatRemoveWorksRight() {
        assertTrue(userRepository.findAll().isEmpty());

        final User link = userRepository.save(new User()
            .setName("Test User"));

        userRepository.delete(link);
        assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatFindAllWorksRight() {
        final List<User> links = List.of(
            new User().setName("Test User"),
            new User().setName("Test User 2")
        );

        userRepository.saveAll(links);
        assertEquals(links.size(), userRepository.findAll().size());
    }
}