package org.baouz.libraryapi.book;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.baouz.libraryapi.common.PageResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void allBooks() {

        int page = 0;
        int size = 10;
        Book book = Book.builder()
                .id("22")
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("999")
                .createdBy("me")
                .build();
        Book secondBook = Book.builder()
                .id("33")
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("88")
                .createdBy("me")
                .build();
        bookRepository.save(book);
        bookRepository.save(secondBook);
        Page<Book> bookPage = new PageImpl<>(List.of(book, secondBook));
        BookResponse bookResponse = BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .year(book.getYear())
                .isbn(book.getIsbn())
                .build();
        when(bookRepository.findAll(PageRequest.of(page, size))).thenReturn(bookPage);
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);

        PageResponse<BookResponse> allBooks = bookService.allBooks(page, size);
        assertNotNull(allBooks);
        assertEquals(bookPage.getTotalElements(), allBooks.getTotalElements());
        assertEquals(bookPage.getTotalPages(), allBooks.getTotalPages());
        assertEquals(bookPage.getContent().size(), allBooks.getContent().size());




    }

    @Test
    void shouldReturnBookById() {
        String id = "11";
        Book book = Book.builder().id(id)
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("999")
                .createdBy("me")
                .build();
        BookResponse bookResponse = BookResponse.builder()
                .id(id)
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("999")
                .build();
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        BookResponse response = bookService.getBookById(id);
        assertNotNull(response);
        assertEquals(bookResponse.getTitle(), response.getTitle());
        assertEquals(bookResponse.getAuthor(), response.getAuthor());
        assertEquals(bookResponse.getDescription(), response.getDescription());
        assertEquals(bookResponse.getYear(), response.getYear());
        assertEquals(bookResponse.getIsbn(), response.getIsbn());

        verify(bookMapper, times(1)).toBookResponse(book);
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    public void shouldThrowsEntityExistsExceptionWhenBookNotFound() {
        String id = "11111111";
        assertThrows(EntityNotFoundException.class,() -> bookService.getBookById(id));
    }

    @Test
    void shouldSaveBook() {
        BookRequest request = BookRequest.builder()
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("999")
                .build();
        Book book = Book.builder()
                .id("99")
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("999")
                .createdBy("me")
                .build();
        Book savedBook = Book.builder()
                .id("99")
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("999")
                .createdBy("me")
                .build();
        BookResponse bookResponse = BookResponse.builder()
                .id("99")
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("999")
                .build();
        when(bookMapper.toBook(request)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.toBookResponse(savedBook)).thenReturn(bookResponse);

        BookResponse saved = bookService.save(request);
        assertNotNull(saved);
        assertEquals(bookResponse.getTitle(), saved.getTitle());
        assertEquals(bookResponse.getAuthor(), saved.getAuthor());
        assertEquals(bookResponse.getDescription(), saved.getDescription());
        assertEquals(bookResponse.getYear(), saved.getYear());
        assertEquals(bookResponse.getIsbn(), saved.getIsbn());

    }
    @Test
    void shouldThrowsEntityExistsExceptionWhenBookAlreadyExists() {
        Book book = Book.builder()
                .id("22")
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("999")
                .createdBy("me")
                .build();
        bookRepository.save(book);

        BookRequest bookRequest = BookRequest.builder()
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("999")
                .build();

        when(bookRepository.findByIsbn("999")).thenReturn(Optional.of(book));

        assertThrows(EntityExistsException.class, () -> bookService.save(bookRequest));
        verify(bookRepository, times(1)).findByIsbn("999");
    }
}