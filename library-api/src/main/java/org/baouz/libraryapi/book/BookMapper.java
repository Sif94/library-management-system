package org.baouz.libraryapi.book;

import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .year(book.getYear())
                .coverPicture(book.getCoverPicture())
                .build();
    }

    public Book toBook(BookRequest request) {
        return Book.builder()
                .title(request.title())
                .author(request.author())
                .description(request.description())
                .year(request.year())
                .isbn(request.isbn())
                .build();
    }
}
