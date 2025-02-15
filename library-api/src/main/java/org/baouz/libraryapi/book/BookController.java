package org.baouz.libraryapi.book;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.libraryapi.common.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {
    private final BookService service;

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> getAllBooks(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok(service.allBooks(page,size));
    }

    @GetMapping("/{book-id}")
    public ResponseEntity<BookResponse> getBookById(
            @PathVariable(name = "book-id") String bookId
    ){
        return ResponseEntity.ok(service.getBookById(bookId));
    }

    @PostMapping
    public ResponseEntity<BookResponse> saveBook(
            @RequestBody @Valid BookRequest request
    ){
        return ResponseEntity
                .status(CREATED)
                .body(service.save(request));
    }
}
