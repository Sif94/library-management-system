package org.baouz.libraryapi.borrowtransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, String> {

    @Query("""
        SELECT COUNT(b)  FROM BorrowTransaction b  WHERE b.work.id = :workId""")
    long countByWorkId(@Param("workId") String workId);
}
