package innohackatons.repository;

import innohackatons.entity.Bank;
import innohackatons.entity.Cashback;
import innohackatons.entity.CashbackId;
import innohackatons.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CashbackRepository extends JpaRepository<Cashback, CashbackId> {
    List<Cashback> findByBank(Bank bank);
    Cashback findByBankAndCategory(Bank bank, Category category);
}
