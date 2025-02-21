package org.baouz.libraryapi.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookResponse {
    private String id;
    private String title;
    private String description;
    private String author;
    private int year;
    private String isbn;
    private String coverPicture;
}
