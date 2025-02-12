package org.baouz.libraryapi.work;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.libraryapi.borrowtransaction.BorrowTransaction;
import org.baouz.libraryapi.common.BaseEntity;

import java.util.Set;

import static jakarta.persistence.DiscriminatorType.STRING;
import static jakarta.persistence.GenerationType.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "work_type", discriminatorType = STRING)
@Entity
public class Work extends BaseEntity {
    @Id @GeneratedValue(strategy = UUID)
    @Column(name = "work_id")
    private String id;
    private String title;
    private String description;
    private String author;

    @OneToMany(mappedBy = "work")
    private Set<BorrowTransaction> borrowTransactions;
}
