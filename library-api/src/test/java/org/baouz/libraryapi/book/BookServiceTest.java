package org.baouz.libraryapi.book;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.baouz.libraryapi.borrowtransaction.BorrowTransactionRepository;
import org.baouz.libraryapi.common.PageResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    private BorrowTransactionRepository borrowTransactionRepository;
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
        BookResponse bookResponse = Mockito.mock(BookResponse.class);
        Page<Book> bookPage = Mockito.mock(Page.class);

        when(bookRepository.findAll(Mockito.any(Pageable.class))).thenReturn(bookPage);
        when(bookMapper.toBookResponse(Mockito.any(Book.class))).thenReturn(bookResponse);
        PageResponse<BookResponse> allBooks = bookService.allBooks(0, 10);
        assertNotNull(allBooks);
    }

    @Test
    void shouldReturnBookById() {
        String id = "11";
        Book book = Book.builder()
                .id(id)
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

    @Test
    void shouldUpdateABookById(){
        String bookId = "11231";
        Book book = Book.builder()
                .id(bookId)
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("999")
                .createdBy("me")
                .build();
        BookRequest bookRequest = BookRequest.builder()
                .title("title2")
                .author("author2")
                .description("description2")
                .year(2022)
                .isbn("9992")
                .build();
        Book mappedBook = Book.builder()
                .title("title2")
                .author("author2")
                .description("description2")
                .year(2022)
                .isbn("9992")
                .build();
        Book updatedBook = Book.builder()
                .id(bookId)
                .title("title2")
                .author("author2")
                .description("description2")
                .year(2022)
                .isbn("9992")
                .createdBy("me")
                .build();
        BookResponse bookResponse = BookResponse.builder()
                .id(bookId)
                .title("title2")
                .author("author2")
                .description("description2")
                .year(2022)
                .isbn("9992")
                .build();
        bookRepository.save(book);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toBook(bookRequest)).thenReturn(mappedBook);
        mappedBook.setId(bookId);
        mappedBook.setCreatedBy("me");
        when(bookRepository.save(mappedBook)).thenReturn(updatedBook);
        when(bookMapper.toBookResponse(updatedBook)).thenReturn(bookResponse);


        BookResponse updatedBookResponse = bookService.updateBook(bookId, bookRequest);

        assertNotNull(updatedBookResponse);
        assertEquals(updatedBookResponse.getTitle(), bookRequest.title());
        assertEquals(updatedBookResponse.getAuthor(), bookRequest.author());
        assertEquals(updatedBookResponse.getDescription(), bookRequest.description());
        assertEquals(updatedBookResponse.getYear(), bookRequest.year());
        assertEquals(updatedBookResponse.getIsbn(), bookRequest.isbn());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(book);
        verify(bookMapper, times(1)).toBook(bookRequest);
        verify(bookMapper, times(1)).toBookResponse(updatedBook);

    }

    @Test
    void shouldThrowsEntityNotFoundExceptionWhenUpdateBookDoesNotExist() {
        String bookId = "11231";
        BookRequest bookRequest = BookRequest.builder()
                .title("title2")
                .author("author2")
                .description("description2")
                .year(2022)
                .isbn("9992")
                .build();
        assertThrows(EntityNotFoundException.class,
                () -> bookService.updateBook(bookId,bookRequest));
    }


    @Test
    void shouldDeleteBookById(){
        String bookId = "22";
        Book book = Book.builder()
                .id(bookId)
                .title("title")
                .author("author")
                .description("description")
                .year(2020)
                .isbn("999")
                .createdBy("me")
                .build();
        bookRepository.save(book);
        when(borrowTransactionRepository.countByWorkId(bookId)).thenReturn(0L);

        bookService.deleteBook(bookId, true);
        verify(bookRepository, times(1)).deleteById(bookId);
        verify(borrowTransactionRepository, times(1)).countByWorkId(bookId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        assertFalse(optionalBook.isPresent());
    }

    
}