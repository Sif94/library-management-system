package org.baouz.libraryapi.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookResponse implements Serializable {
    private String id;
    private String title;
    private String description;
    private String author;
    private int year;
    private String isbn;
    private String coverPicture;
    private Boolean available;
}
