package org.baouz.libraryapi.book;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.baouz.libraryapi.common.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final BookMapper mapper;

    public PageResponse<BookResponse> allBooks(int page, int size) {
        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(page, size));

        List<BookResponse> bookResponses = bookPage
                .getContent()
                .stream()
                .map(mapper::toBookResponse)
                .toList();

        return PageResponse.<BookResponse>builder()
                .page(bookPage.getNumber())
                .size(bookPage.getSize())
                .totalPages(bookPage.getTotalPages())
                .totalElements(bookPage.getTotalElements())
                .content(bookResponses)
                .isFirst(bookPage.isFirst())
                .isLast(bookPage.isLast())
                .build();
    }

    public BookResponse getBookById(String bookId) {
        return bookRepository
                .findById(bookId)
                .map(mapper::toBookResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Book with ID:: %s not found", bookId))
                );
    }

    @Transactional
    public BookResponse save(BookRequest request) {
        bookRepository.findByIsbn(request.isbn()).ifPresent((b) -> {
            throw new EntityExistsException(format("Book with ISBN:: %s already exists", b.getIsbn()));
        });

        Book newBook =mapper.toBook(request);
        Book savedBook = bookRepository.save(newBook);

        return mapper.toBookResponse(savedBook);
    }
}
