package innohackatons.repository;

import innohackatons.entity.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.category.categoryId = :categoryId "
           + "AND t.date BETWEEN :dateFrom AND :dateTo")
    List<Transaction> findTransactionsByUserIdAndCategoryIdAndDateRange(
        @Param("userId") long userId,
        @Param("categoryId") Long categoryId,
        @Param("dateFrom") LocalDateTime dateFrom,
        @Param("dateTo") LocalDateTime dateTo
    );
}
