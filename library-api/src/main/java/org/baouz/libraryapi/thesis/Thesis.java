package org.baouz.libraryapi.thesis;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.libraryapi.borrowtransaction.BorrowTransaction;
import org.baouz.libraryapi.work.Work;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "theses")
//@DiscriminatorValue(value = "THESIS")
public class Thesis extends Work {
    @Column(nullable = false)
    private LocalDate dateOfGraduation;

    @OneToMany
    private Set<BorrowTransaction> borrowTransactions;
}
