package org.baouz.libraryapi.book;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.libraryapi.common.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping("/{book-id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable("book-id") String bookId,
            @Valid @RequestBody BookRequest request
    ){
        return ResponseEntity.ok(service.updateBook(bookId, request));
    }

    @DeleteMapping("/{book-id}")
    public ResponseEntity<?> deleteBookById(
            @PathVariable(name = "book-id") String bookId,
            @RequestParam(name = "confirm", defaultValue = "false") boolean confirm
    ){
        service.deleteBook(bookId, confirm);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("book-id") String bookId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ) {
        service.uploadBookCoverPicture(file,connectedUser, bookId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/available")
    public ResponseEntity<PageResponse<BookResponse>> getAvailableBooks(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.ok(service.getAvailableBooks(page,size));
    }

    @PatchMapping("/available/{book-id}")
    public ResponseEntity<BookResponse> updateAvailableStatus(
            @PathVariable("book-id") String bookId
    ){
        return ResponseEntity.ok(service.updateAvailableStatus(bookId));
    }
}
