package org.baouz.libraryapi.faculty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FacultyRequest(
        @NotNull(message = "Faculty Name is required")
        @NotEmpty(message = "Faculty Name is required")
        @NotBlank(message = "Faculty Name is required")
        String name,
        String description
) {
}
