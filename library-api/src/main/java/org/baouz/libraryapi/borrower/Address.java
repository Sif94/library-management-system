package org.baouz.libraryapi.borrower;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;
}
