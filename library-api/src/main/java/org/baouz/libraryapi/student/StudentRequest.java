package org.baouz.libraryapi.student;

import jakarta.validation.constraints.*;
import org.baouz.libraryapi.borrower.Address;
import org.baouz.libraryapi.borrower.Gender;

import java.time.LocalDate;

public record StudentRequest(
        @NotNull(message = "Firstname is required")
        @NotEmpty(message = "Firstname is required")
        @NotBlank(message = "Firstname is required")
        String firstname,
        @NotNull(message = "Lastname is required")
        @NotEmpty(message = "Lastname is required")
        @NotBlank(message = "Lastname is required")
        String lastname,
        @NotNull(message = "Email is required")
        @NotEmpty(message = "Email is required")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,
        @Pattern(regexp = "^\\+(?:[0-9]●?){6,14}[0-9]$", message = "Phone is invalid")
        String phone,
        Address address,
        @NotNull(message = "Birthday is required")
        LocalDate birthday,
        @NotNull(message = "Gender is required")
        Gender gender,
        @NotNull(message = "Student code is required")
        @NotEmpty(message = "Student code is required")
        @NotBlank(message = "Student code is required")
        String studentCode,
        @NotNull(message = "Baccalaureate year is required")
        int baccalaureateYear,
        @NotNull(message = "Baccalaureate name is required")
        @NotEmpty(message = "Baccalaureate name is required")
        @NotBlank(message = "Baccalaureate name is required")
        String baccalaureateName,
        @NotNull(message = "Department ID name is required")
        @NotEmpty(message = "Department ID name is required")
        @NotBlank(message = "Department ID name is required")
        String departmentId
) {
}
