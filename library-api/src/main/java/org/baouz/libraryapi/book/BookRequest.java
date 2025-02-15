package org.baouz.libraryapi.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BookRequest(
        @NotNull(message = "Book title is mandatory")
        @NotBlank(message = "Book title is mandatory")
        @NotEmpty(message = "Book title is mandatory")
        String title,
        String description,
        @NotNull(message = "Book author is mandatory")
        @NotBlank(message = "Book author is mandatory")
        @NotEmpty(message = "Book author is mandatory")
        String author,
        @NotNull(message = "Book year is mandatory")
        int year,
        @NotNull(message = "Book isbn is mandatory")
        @NotBlank(message = "Book isbn is mandatory")
        @NotEmpty(message = "Book isbn is mandatory")
        String isbn
) {
}
