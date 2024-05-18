package innohackatons.repository;

import innohackatons.entity.PiggyBank;
import innohackatons.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiggyBankRepository extends JpaRepository<PiggyBank, Long> {
    List<PiggyBank> findByUser(User user);

    Optional<PiggyBank> findByUserAndGoal(User user, String goal);
}
