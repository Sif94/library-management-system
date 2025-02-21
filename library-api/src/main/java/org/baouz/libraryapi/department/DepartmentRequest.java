package org.baouz.libraryapi.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DepartmentRequest(
        @NotNull(message = "Department name is required")
        @NotBlank(message = "Department name is required")
        @NotEmpty(message = "Department name is required")
        String name,
        String description,
        @NotNull(message = "Department faculty Id is required")
        @NotBlank(message = "Department faculty Id is required")
        @NotEmpty(message = "Department faculty Id is required")
        String facultyId
) {
}
