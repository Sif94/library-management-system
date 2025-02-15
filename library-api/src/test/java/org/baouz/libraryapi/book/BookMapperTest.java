package org.baouz.libraryapi.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class BookMapperTest {


    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();
    }

    @Test
    void toBookResponse() {
        // Arrange
        Book book = Book.builder()
                .isbn("111")
                .title("Book Title")
                .author("Author")
                .description("Book Description")
                .year(2021)
                .createdBy("me")
                .createdDate(LocalDateTime.now())
                .build();
        // Act
        BookResponse bookResponse = bookMapper.toBookResponse(book);

        // Assert
        assertNotNull(bookResponse);
        assertEquals(bookResponse.getIsbn(), book.getIsbn());
        assertEquals(bookResponse.getTitle(), book.getTitle());
        assertEquals(bookResponse.getAuthor(), book.getAuthor());
        assertEquals(bookResponse.getDescription(), book.getDescription());
        assertEquals(bookResponse.getYear(), book.getYear());

    }

    @Test
    void toBook() {
        // Arrange
        BookRequest request = BookRequest.builder()
                .isbn("111")
                .title("Book Title")
                .author("Author")
                .description("Book Description")
                .year(2021)
                .build();
        // Act
        Book book = bookMapper.toBook(request);

        // Assert
        assertNotNull(book);
        assertEquals(book.getIsbn(), request.isbn());
        assertEquals(book.getTitle(), request.title());
        assertEquals(book.getAuthor(), request.author());
        assertEquals(book.getDescription(), request.description());
        assertEquals(book.getYear(), request.year());
    }
}