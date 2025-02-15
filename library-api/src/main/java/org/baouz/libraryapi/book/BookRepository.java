package org.baouz.libraryapi.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByAuthor(String author);

}
