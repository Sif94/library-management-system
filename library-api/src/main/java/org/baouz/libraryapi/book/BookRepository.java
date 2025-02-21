package org.baouz.libraryapi.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    
    Optional<Book> findByIsbn(String isbn);
    Page<Book> findByAuthor(String author, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.available = true")
    Page<Book> findAllAvailableBooks(Pageable pageable);

}
