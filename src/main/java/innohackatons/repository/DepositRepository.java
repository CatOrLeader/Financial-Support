package innohackatons.repository;

import innohackatons.entity.Deposit;
import innohackatons.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    Optional<Deposit> findByUserIdAndBankId(Long userId, Long bankId);

    List<Deposit> findByUser(User user);
}
