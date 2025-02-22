package org.baouz.libraryapi.borrower;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Address {
    @NotNull(message = "Street is required")
    @NotEmpty(message = "Street is required")
    @NotBlank(message = "Street is required")
    private String street;
    @NotNull(message = "City is required")
    @NotEmpty(message = "City is required")
    @NotBlank(message = "City is required")
    private String city;
    @NotNull(message = "State is required")
    @NotEmpty(message = "State is required")
    @NotBlank(message = "State is required")
    private String state;
    @NotNull(message = "Zip code is required")
    @NotEmpty(message = "Zip code is required")
    @NotBlank(message = "Zip code is required")
    private String zip;
}
