package org.baouz.libraryapi.borrowtransaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.libraryapi.borrower.Borrower;
import org.baouz.libraryapi.work.Work;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(
        name = "borrow_transactions"
)
public class BorrowTransaction {
    @Id @GeneratedValue(strategy = UUID)
    @Column(name = "borrow_transaction_id")
    private String id;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    @ManyToOne
    @JoinColumn(name = "work_id", nullable = false)
    private Work work;
    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    private Borrower borrower;
}
