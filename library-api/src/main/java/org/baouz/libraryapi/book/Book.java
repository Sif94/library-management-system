package org.baouz.libraryapi.book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.libraryapi.work.Work;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "BOOKS")
//@DiscriminatorValue(value = "BOOK")
@PrimaryKeyJoinColumn(name = "book_id", referencedColumnName = "work_id")

public class Book extends Work {
    @Column(unique = true, nullable = false)
    private String isbn;
    private int year;
    private String coverPicture;


}
