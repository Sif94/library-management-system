package org.baouz.libraryapi.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;


    @Test
    void itShouldCheckIfABookIsFindByIsbn() {

        // Arrange
        String isbn = "22222";
        Book book = Book.builder()
                .isbn(isbn)
                .title("Book Title")
                .author("Author")
                .description("Book Description")
                .year(2021)
                .createdBy("me")
                .createdDate(LocalDateTime.now())
                .build();
        bookRepository.save(book);

        // Act
        var savedBook = bookRepository.findByIsbn(isbn).get();

        // Assert
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Book Title");
        assertThat(savedBook.getAuthor()).isEqualTo("Author");
        assertThat(savedBook.getDescription()).isEqualTo("Book Description");
        assertThat(savedBook.getYear()).isEqualTo(2021);
        assertThat(savedBook.getCreatedBy()).isEqualTo("me");
        assertThat(isbn).isEqualTo(savedBook.getIsbn());
    }

}