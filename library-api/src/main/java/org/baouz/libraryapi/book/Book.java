package org.baouz.libraryapi.book;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.libraryapi.borrowtransaction.BorrowTransaction;
import org.baouz.libraryapi.work.Work;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "books")
//@DiscriminatorValue(value = "BOOK")
public class Book extends Work {
    @Column(unique = true, nullable = false)
    private int isbn;
    private int year;
    private String coverPicture;


}
