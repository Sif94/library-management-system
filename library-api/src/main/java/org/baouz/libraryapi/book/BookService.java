package org.baouz.libraryapi.book;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baouz.libraryapi.borrowtransaction.BorrowTransactionRepository;
import org.baouz.libraryapi.common.PageResponse;
import org.baouz.libraryapi.file.FileStorageService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    private final BookMapper mapper;

    private final BorrowTransactionRepository borrowTransactionRepository;
    private final FileStorageService fileStorageService;

    @Cacheable(value = "booksPages", key = "'page_' + #page + '_size_' + #size")
    public PageResponse<BookResponse> allBooks(int page, int size) {
        log.info("Fetching all books from the database");
        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(page, size));

        return getBookResponsePageResponse(bookPage);
    }



    @Cacheable(value = "books", key = "#bookId")
    public BookResponse getBookById(String bookId) {
        log.info("Fetching book with ID:: {}", bookId);
        return bookRepository
                .findById(bookId)
                .map(mapper::toBookResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Book with ID:: %s not found", bookId))
                );
    }

    @Transactional
    @Caching(
            put = { @CachePut(value = "books", key = "#result.id") },
            evict = {
                    @CacheEvict(value = "booksPages", allEntries = true) ,
                    @CacheEvict(value = "availableBooksPages", allEntries = true)
            }
    )
    public BookResponse save(BookRequest request) {
        log.info("Saving new book to the database");
        bookRepository.findByIsbn(request.isbn()).ifPresent(b -> {
            throw new EntityExistsException(format("Book with ISBN:: %s already exists", b.getIsbn()));
        });

        Book newBook = mapper.toBook(request);
        newBook.setAvailable(true);
        Book savedBook = bookRepository.save(newBook);
        return mapper.toBookResponse(savedBook);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "books", key = "#bookId"),
                    @CacheEvict(value = "booksPages", allEntries = true),
                    @CacheEvict(value = "availableBooksPages", allEntries = true)
            }
    )
    public void deleteBook(String bookId, boolean confirm) {
        log.info("Deleting book with ID:: {}", bookId);
        long transactionsCount = borrowTransactionRepository.countByWorkId(bookId);

        final List<String> warnings = new ArrayList<>();
        if (transactionsCount > 0) {
            warnings.add("Transactions count is greater than 0");
            System.out.println("The current book has transactions: " + transactionsCount);
        }
        if (!warnings.isEmpty() && !confirm) {
            throw new RuntimeException("One or more warnings");
        }

        bookRepository.deleteById(bookId);
    }

    @Transactional
    @Caching(
            put = { @CachePut(value = "books", key = "#bookId") },
            evict = {
                    @CacheEvict(value = "booksPages", allEntries = true),
                    @CacheEvict(value = "availableBooksPages", allEntries = true)
            }
    )
    public BookResponse updateBook(String bookId, BookRequest request) {
        log.info("Updating book with ID:: {}", bookId);
        bookRepository.findById(bookId)
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Book with ID:: %s not found", bookId))
                );
        Book updatedBook = mapper.toBook(request);
        updatedBook.setId(bookId);
        Book savedBook = bookRepository.save(updatedBook);
        return mapper.toBookResponse(savedBook);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "books", key = "#bookId"),
                    @CacheEvict(value = "booksPages", allEntries = true),
                    @CacheEvict(value = "availableBooksPages", allEntries = true)
            }
    )
    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, String bookId) {
        log.info("Uploading book cover picture for book with ID:: {}", bookId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        var profilePicture = fileStorageService.saveFile(file, connectedUser.getName());
        book.setCoverPicture(profilePicture);
        bookRepository.save(book);
    }

    @Cacheable(value = "availableBooksPages", key = "'page_' + #page + '_size_' + #size")
    public PageResponse<BookResponse> getAvailableBooks(int page, int size) {
        log.info("Fetching all available books from the database");
        Page<Book> bookPage = bookRepository.findAllAvailableBooks(PageRequest.of(page, size));

        return getBookResponsePageResponse(bookPage);
    }

    private PageResponse<BookResponse> getBookResponsePageResponse(Page<Book> bookPage) {
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

    @Transactional
    @Caching(
            put = { @CachePut(value = "books", key = "#bookId") },
            evict = {
                    @CacheEvict(value = "booksPages", allEntries = true),
                    @CacheEvict(value = "availableBooksPages", allEntries = true)
            }
    )
    public BookResponse updateAvailableStatus(String bookId) {
        log.info("Updating book availability status for book with ID:: {}", bookId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        book.setAvailable(!book.isAvailable());
        Book savedBook = bookRepository.save(book);
        return mapper.toBookResponse(savedBook);
    }
}
