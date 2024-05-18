package innohackatons.repository;

import innohackatons.entity.Cashback;
import innohackatons.entity.CashbackId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashbackRepository extends JpaRepository<Cashback, CashbackId> {
}
