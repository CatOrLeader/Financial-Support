package innohackatons.repository;

import innohackatons.entity.Bank;
import innohackatons.entity.Cashback;
import innohackatons.entity.CashbackId;
import innohackatons.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashbackRepository extends JpaRepository<Cashback, CashbackId> {
    List<Cashback> findByBank(Bank bank);

    Cashback findByBankAndCategory(Bank bank, Category category);
}
